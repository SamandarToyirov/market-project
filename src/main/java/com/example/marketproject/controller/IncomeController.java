package com.example.marketproject.controller;


import com.example.marketproject.entity.IncomeProduct;
import com.example.marketproject.entity.Product;
import com.example.marketproject.repo.CategoryRepo;
import com.example.marketproject.repo.IncomeProductRepo;
import com.example.marketproject.repo.ProductRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeProductRepo incomeProductRepo;
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @GetMapping("")
    public String toIncomePage(@RequestParam UUID productId, Model model){

        model.addAttribute("productId", productId);


        return "makeIncome";

    }

    @PostMapping("")
    public String makeIncome(@RequestParam UUID productId,
                             @RequestParam Integer price,
                             @RequestParam Integer amount,
                             Model model){

        Product product = productRepo.findById(productId).get();
        System.out.println("aaaaaaaaaaa");
        IncomeProduct incomeProduct = IncomeProduct.builder()
                .product(product)
                .price(price)
                .amount(amount)
                .build();
        incomeProductRepo.save(incomeProduct);

//        model.addAttribute("categories", categoryRepo.findAll());
//        model.addAttribute("products", productRepo.findAllByCategoryId(product.getCategory().getId()));
//        model.addAttribute("incomeProducts",incomeProductRepo.findAllByProductId(productId));
//


        return "redirect:/?categoryId="+product.getCategory().getId()+"&productId="+product.getId()+"&button=income";

    }

}
