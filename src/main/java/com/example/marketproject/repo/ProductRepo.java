package com.example.marketproject.repo;

import com.example.marketproject.entity.Product;
import com.example.marketproject.projection.BalanceReportProjection;
import com.example.marketproject.projection.ProductSaleReportProjection;
import com.example.marketproject.projection.ProfitReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {


    List<Product> findAllByCategoryId(UUID categoryId);

    List<Product> findAllByNameContainingIgnoreCaseAndCategoryId(String search, UUID categoryId);


    @Query(nativeQuery = true, value = """
            SELECT
                p.name,
                COALESCE(s.saleCount, 0) AS saleCount,
                COALESCE(s.saleSumm, 0) AS saleSumm,
                COALESCE(s.totalSum, 0) AS totalSum
            FROM
                product p
                    LEFT JOIN (
                    SELECT
                        product_id,
                        COUNT(id) AS saleCount,
                        SUM(amount) AS saleSumm,
                        SUM(sale_price * amount) AS totalSum
                    FROM
                        sale
                    GROUP BY
                        product_id
                ) s ON p.id = s.product_id;

            """)
    List<ProductSaleReportProjection> getProductSaleReport();

    @Query(nativeQuery = true, value = """


            SELECT
               p.name AS name,
               COALESCE(ip.incomes, 0)
               -COALESCE(s.sales, 0) AS remain
           FROM
               product p
                   LEFT JOIN (
                   SELECT
                       product_id,
                       SUM(amount) AS incomes
                   FROM
                       income_product
                   GROUP BY
                       product_id
               ) ip ON p.id = ip.product_id
                   LEFT JOIN (
                   SELECT
                       product_id,
                       SUM(amount) AS sales
                   FROM
                       sale
                   GROUP BY
                       product_id
               ) s ON p.id = s.product_id order by remain;      
                    """)
    List<BalanceReportProjection> getBalanceProjectionReport();


    @Query(nativeQuery = true, value = """

            WITH aggregated_sales AS (
                    SELECT
            product_id,
                        SUM(amount) AS
            total_sales_amount,
            SUM(sale_price * amount) AS total_sales_sum
                    FROM
                        sale
                    GROUP BY
            product_id
                ),
            income_ranked AS (
                         SELECT
                             ip.product_id,
                             ip.price,
                             ip.amount,
                             SUM(ip.amount)
            OVER (PARTITION BY ip.product_id ORDER BY ip.id) AS cumulative_amount
                         FROM
            income_product ip
                     ),
                     limited_incomes AS (
                         SELECT
                             ir.product_id
            ,
                             CASE
                                 WHEN ir.cumulative_amount <= ags.total_sales_amount THEN ir.amount
                                 ELSE GREATEST(ags.total_sales_amount - (ir.cumulative_amount - ir.amount), 0)
                                 END AS limited_amount,
                             ir.price
            FROM
            income_ranked ir
                                 JOIN
            aggregated_sales ags ON ir.product_id = ags.product_id
                     ),
            total_limited_incomes AS (
                         SELECT
            product_id,
                             SUM(limited_amount * limited_incomes.price) AS total_limited_income_sum
                         FROM
                             limited_incomes
                         GROUP BY
                             product_id
                     )
                SELECT
                    p.name
            ,
                    COALESCE(ags
            .total_sales_sum, 0) - COALESCE(tli.total_limited_income_sum, 0) AS profit
                FROM
                    product p
                        LEFT JOIN aggregated_sales ags ON p.id = ags.product_id
                        LEFT JOIN total_limited_incomes tli ON p.id = tli.product_id;
                
                """)
    List<ProfitReportProjection> getEachProductProfit();

}
