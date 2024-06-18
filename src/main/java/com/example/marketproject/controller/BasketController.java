package com.example.marketproject.controller;

import com.example.marketproject.dto.ProductAddReportDto;
import com.example.marketproject.entity.Basket;
import com.example.marketproject.entity.Product;
import com.example.marketproject.entity.Sale;
import com.example.marketproject.repo.*;
import com.example.marketproject.service.PDFService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final IncomeProductRepo incomeProductRepo;
    private final SaleRepo saleRepo;
    private final PDFService pdfService;

    @GetMapping("/add")
    public String addBasket(HttpSession session, Model model){

        Object basketObj = session.getAttribute("basket");
        ProductAddReportDto productSale = (ProductAddReportDto) session.getAttribute("productSale");
        Basket basket;

        boolean hasOld=true;
        if (basketObj!=null){
            basket=(Basket) basketObj;


            for (ProductAddReportDto productAddReportDto : basket.getReportDtoList()) {

                if (productAddReportDto.getProductId().equals(productSale.getProductId())){
                    productAddReportDto.setAmount(productSale.getAmount());
                    productAddReportDto.setRemain(productSale.getRemain());
                    hasOld=false;
                    break;
                }

            }
            if (hasOld){
                basket.getReportDtoList().add(productSale);
            }

            session.setAttribute("basket", basket);
        }
        else{
            basket = new Basket();
            basket.getReportDtoList().add(productSale);
            session.setAttribute("basket", basket);

        }

        Product product = productRepo.findById(productSale.getProductId()).get();

        Integer totalsum=0;

        for (var pr : basket.getReportDtoList()) {
            totalsum+=pr.getPrice()*pr.getAmount();
        }

        model.addAttribute("productId",product.getId());
        model.addAttribute("categoryId",product.getCategory().getId());
        model.addAttribute("products",productRepo.findAllByCategoryId(product.getCategory().getId()));
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("basket",basket.getReportDtoList());
        model.addAttribute("totalSum",totalsum);


        return "sales";


    }

    @GetMapping("/remove")
    public String remove(@RequestParam UUID productId, HttpSession session, Model model){

        Basket basket = (Basket) session.getAttribute("basket");


        List<ProductAddReportDto> reportDtoList = basket.getReportDtoList();
        for (ProductAddReportDto product : reportDtoList) {

            if (product.getProductId().equals(productId)){
                reportDtoList.remove(product);
                break;
            }

        }
        basket.setReportDtoList(reportDtoList);
        session.setAttribute("basket", basket);

        Product product = productRepo.findById(productId).get();
        model.addAttribute("products", productRepo.findAllByCategoryId(product.getCategory().getId()));
        model.addAttribute("categories"," ");
        return "redirect:/sale?categoryId="+product.getCategory().getId()+"&productId="+productId;

    }

    @GetMapping("/purchase")
    public String purchase(HttpSession session, Model model) throws IOException {

        Basket basket = (Basket) session.getAttribute("basket");


        for (var pr : basket.getReportDtoList()) {

            Sale sale = Sale.builder()
                    .amount(pr.getAmount())
                    .saledAt(LocalDateTime.now())
                    .salePrice(pr.getPrice())
                    .product(productRepo.findById(pr.getProductId()).get())
                    .build();

            saleRepo.save(sale);

        }
        basket.setReportDtoList(new ArrayList<>());

        session.setAttribute("productReport",null);
        model.addAttribute("categories", categoryRepo.findAll());
        return "sales";
    }





}
