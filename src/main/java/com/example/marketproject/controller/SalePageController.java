package com.example.marketproject.controller;

import com.example.marketproject.dto.ProductAddReportDto;
import com.example.marketproject.entity.Basket;
import com.example.marketproject.entity.Product;
import com.example.marketproject.projection.ProductAddReportProjection;
import com.example.marketproject.repo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SalePageController {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final IncomeProductRepo incomeProductRepo;
    private final SaleRepo saleRepo;


    @GetMapping("")
    public String salePage(
            Model model,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID productId,
            HttpSession session

            ){

        model.addAttribute("categories",categoryRepo.findAll());


        Basket basket=new Basket();
        Object object = session.getAttribute("basket");

        if (object!=null){
            basket=(Basket) object;

            Integer totalSum=0;

            for (var pr : basket.getReportDtoList()) {
                totalSum+=pr.getAmount()*pr.getPrice();
            }
            model.addAttribute("totalSum", totalSum);

        }




        if (categoryId!=null) {

            List<Product> products = productRepo.findAllByCategoryId(categoryId);
            model.addAttribute("products", products);
            model.addAttribute("categoryId", categoryId);

            boolean hasBasket=true;
            ProductAddReportDto productAddReportDto = null;

            if (productId!=null){
                Product product = productRepo.findById(productId).get();

                if (basket!=null){

                    for (ProductAddReportDto pr : basket.getReportDtoList()) {

                        if (pr.getProductId().equals(productId)){
                            productAddReportDto=pr;
                            hasBasket=false;
                        }
                    }
                }

                if(hasBasket){
                    ProductAddReportProjection productAddReport = saleRepo.getProductAddReport(productId);

                     productAddReportDto = ProductAddReportDto.builder()
                            .productId(productAddReport.getId())
                            .price(productAddReport.getPrice())
                            .amount(productAddReport.getAmount())
                            .name(productAddReport.getName())
                            .remain(productAddReport.getRemain())
                            .build();
                }


                if (productAddReportDto.getRemain()<=0){
                    model.addAttribute("productReport",null);
                }
                else{
                    model.addAttribute("productReport",productAddReportDto);
                }
                session.setAttribute("productSale",productAddReportDto);
                model.addAttribute("product",product);
                model.addAttribute("basket", basket.getReportDtoList());
            }

        }



        return "sales";
    }

}
