package com.example.marketproject.repo;

import com.example.marketproject.entity.Sale;
import com.example.marketproject.projection.ProductAddReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SaleRepo extends JpaRepository<Sale, UUID> {
    List<Sale> findAllByProductId(UUID productId);

    @Query(nativeQuery = true, value = """
            SELECT
                p.id,
                p.name,
                p.price,
                COALESCE(ip.total_incoming, 0) - COALESCE(s.total_sold, 0)-1 AS remain,
                1 amount
            FROM
                product p
                    LEFT JOIN (
                    SELECT
                        ip.product_id,
                        SUM(amount) AS total_incoming
                    FROM
                        income_product ip
                    GROUP BY
                        ip.product_id
                ) ip ON p.id = ip.product_id
                    LEFT JOIN (
                    SELECT
                        s.product_id,
                        SUM(amount) AS total_sold
                    FROM
                        Sale s
                    GROUP BY
                        s.product_id
                ) s ON p.id = s.product_id
                where p.id=:productId
                ;
                    """)
    ProductAddReportProjection getProductAddReport(UUID productId);
}
