package com.example.marketproject.controller;

import com.example.marketproject.entity.*;
import com.example.marketproject.entity.enums.RoleName;
import com.example.marketproject.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final IncomeProductRepo incomeProductRepo;
    private final SaleRepo saleRepo;

    @GetMapping("")
    public String test(Principal principal, Model model,
                       @RequestParam(required = false)UUID categoryId,
                       @RequestParam(required = false)UUID productId,
                       @RequestParam(required = false)String button){

        String username = principal.getName();
        User byUsername = userRepo.findByUsername(username);

        List<Role> roles = byUsername.getRoles();

        model.addAttribute("categories",categoryRepo.findAll());

        if (categoryId!=null){
           List<Product> products = productRepo.findAllByCategoryId(categoryId);
           model.addAttribute("products", products);
           model.addAttribute("categoryId", categoryId);

           if (productId!=null){

               model.addAttribute("productId", productId);
               if (button!=null){

                   model.addAttribute("button", button);

                   if (button.equals("info")){
                       Product product = productRepo.findById(productId).get();
                       model.addAttribute("product", product);
                   }

                   if (button.equals("income")){
                       List<IncomeProduct> incomeProducts = incomeProductRepo.findAllByProductId(productId);
                       model.addAttribute("incomeProducts", incomeProducts);

                   }

                   if(button.equals("sale")){
                      List<Sale> sales =  saleRepo.findAllByProductId(productId);
                      model.addAttribute("sales", sales);
                   }



               }
           }

        }

        for (Role role : roles) {

            if (role.getRoleName().equals(RoleName.ROLE_ADMIN)){
                return "admin";
            }
            else {
                return "sales";
            }

        }

        return "admin";

    }

}
