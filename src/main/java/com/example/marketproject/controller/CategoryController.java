package com.example.marketproject.controller;

import com.example.marketproject.entity.Category;
import com.example.marketproject.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepo categoryRepo;


    @GetMapping("/search")
    private String search(@RequestParam String search, Model model){

       List<Category> categories = categoryRepo.findAllByNameContainingIgnoreCase(search);
       model.addAttribute("categories", categories);
       return "admin";

    }

    @GetMapping("/sale/search")
    private String saleSearch(@RequestParam String search, Model model){
        List<Category> categories = categoryRepo.findAllByNameContainingIgnoreCase(search);
        model.addAttribute("categories", categories);
        return "sales";
    }

}
