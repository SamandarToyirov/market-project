package com.example.marketproject.controller;

import com.example.marketproject.dto.ProductAddReportDto;
import com.example.marketproject.entity.Attachment;
import com.example.marketproject.entity.Basket;
import com.example.marketproject.entity.Category;
import com.example.marketproject.entity.Product;
import com.example.marketproject.projection.ProductAddReportProjection;
import com.example.marketproject.repo.AttachmentRepo;
import com.example.marketproject.repo.CategoryRepo;
import com.example.marketproject.repo.ProductRepo;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final AttachmentRepo attachmentRepo;

    @GetMapping("/search")
    public String search(Model model, @RequestParam UUID categoryId,
                         @RequestParam String search){


        List<Product> products = productRepo.findAllByNameContainingIgnoreCaseAndCategoryId(search, categoryId);
        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        model.addAttribute("categoryId",categoryId);

        return "admin";


    }

     @GetMapping("/sale/search")
    public String searchSale(Model model, @RequestParam UUID categoryId,
                         @RequestParam String search){


        List<Product> products = productRepo.findAllByNameContainingIgnoreCaseAndCategoryId(search, categoryId);
        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        model.addAttribute("categoryId",categoryId);

        return "sales";


    }



    @PostMapping("/edit")
    @Transactional
    public String edit(@RequestParam UUID productId,
                       @RequestParam(required = false, name = "file")MultipartFile file,
                       @RequestParam UUID categoryId,
                       @RequestParam Integer price,
                       @RequestParam String name,
                       Model model
                       ) throws IOException {

        Product product = productRepo.findById(productId).get();
        Category category = categoryRepo.findById(categoryId).get();

        Attachment attachment=null;

        if (product.getAttachment()!=null){

             attachment=attachmentRepo.findById(product.getAttachment().getId()).get();
        }
        if (!file.isEmpty()){

            if (product.getAttachment()!=null){
                UUID attachmentId = product.getAttachment().getId();
                product.setAttachment(null);
                product = productRepo.save(product);
                attachmentRepo.deleteById(attachmentId);
            }
             attachment = Attachment.builder()
                    .content(file.getBytes())
                    .build();
             attachment = attachmentRepo.save(attachment);

        }

        product.setPrice(price);
        product.setName(name);
        product.setCategory(category);
        product.setAttachment(attachment);
        productRepo.save(product);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("button", "info");
        model.addAttribute("productId", productId);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("product", product);
        model.addAttribute("products", productRepo.findAllByCategoryId(categoryId));

        return "admin";

    }


    @GetMapping("/add")
    public String toAddProductPage(Model model){

        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        return "addProduct";

    }

    @PostMapping("/add")
    public String addProduct(
            @RequestParam(required = false, name = "photo") MultipartFile file,
            @RequestParam Integer price,
            @RequestParam String  name,
            @RequestParam UUID categoryId

    ) throws IOException {
        Attachment attachment=null;

        if (!file.isEmpty()){
            attachment=Attachment.builder()
                    .content(file.getBytes())
                    .build();
            attachmentRepo.save(attachment);
        }

        Product product = Product.builder()
                .category(categoryRepo.findById(categoryId).get())
                .price(price)
                .name(name)
                .attachment(attachment)
                .build();

        productRepo.save(product);

        return "redirect:/?categoryId="+categoryId.toString()+"&productId"+product.getId().toString()+"&button=info";


    }

    @GetMapping("/sale")
    public String productAddOrDec(@RequestParam String sign, HttpSession session, Model model){
        Object object = session.getAttribute("productSale");
        var productSale = (ProductAddReportDto) object;

        if (sign.equals("plus")){
            if (productSale.getRemain()>=1){
                productSale.setAmount(productSale.getAmount()+1);
                productSale.setRemain(productSale.getRemain()-1);
            }
        }
        else{
            if (productSale.getAmount()>=2){
                productSale.setAmount(productSale.getAmount()-1);
                productSale.setRemain(productSale.getRemain()+1);
            }
        }
        Basket basket = null;
        Object object1 = session.getAttribute("basket");

        Integer totalSum=0;
        if (object1!=null){
            basket = (Basket) object1;

            for (var pr : basket.getReportDtoList()) {
                totalSum+=pr.getAmount()*pr.getPrice();
            }
        }

        else{
            basket=new Basket();
        }

        Product product = productRepo.findById(productSale.getProductId()).get();
        model.addAttribute("basket",basket.getReportDtoList());
        model.addAttribute("product",product);
        model.addAttribute("productReport", productSale);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("categoryId", product.getCategory().getId());
        model.addAttribute("products",productRepo.findAllByCategoryId(product.getCategory().getId()));
        model.addAttribute("totalSum", totalSum);

        session.setAttribute("productReport",productSale);
        session.setAttribute("basket",basket);

        return "sales";

    }


}
