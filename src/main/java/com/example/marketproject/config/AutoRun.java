package com.example.marketproject.config;

import com.example.marketproject.entity.Category;
import com.example.marketproject.entity.IncomeProduct;
import com.example.marketproject.entity.Product;
import com.example.marketproject.entity.Sale;
import com.example.marketproject.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AutoRun implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final IncomeProductRepo incomeProductRepo;
    private final SaleRepo saleRepo;


    @Override
    public void run(String... args) throws Exception {







//        Category category1 = Category.builder()
//                .name("Ichimlik")
//                .build();
//
//         Category category2 = Category.builder()
//                .name("Yegulik")
//                .build();
//
//         Category category3 = Category.builder()
//                .name("Sport")
//                .build();
//
//         categoryRepo.save(category1);
//         categoryRepo.save(category2);
//         categoryRepo.save(category3);
//
//        Product product1 = Product.builder()
//                .name("Olma")
//                .price(10)
//                .category(category2)
//                .build();
//
//         Product product2 = Product.builder()
//                .name("Anor")
//                .price(12)
//                .category(category2)
//                .build();
//
//         Product product3 = Product.builder()
//                .name("Cola")
//                .price(10)
//                .category(category1)
//                .build();
//
//        Product product4 = Product.builder()
//                .name("Fanta")
//                .price(10)
//                .category(category1)
//                .build();
//
//        Product product5 = Product.builder()
//                .name("To'p")
//                .price(120)
//                .category(category3)
//                .build();
//
//        Product product6 = Product.builder()
//                .name("Butsa")
//                .price(180)
//                .category(category3)
//                .build();
//
//        productRepo.save(product1);
//        productRepo.save(product2);
//        productRepo.save(product3);
//        productRepo.save(product4);
//        productRepo.save(product5);
//        productRepo.save(product6);





//        Role adminRole = Role.builder()
//                .roleName(RoleName.ROLE_ADMIN)
//                .build();
//
//         Role salesManager = Role.builder()
//                .roleName(RoleName.ROLE_SALES_MANAGER)
//                .build();
//
//         roleRepo.save(adminRole);
//         roleRepo.save(salesManager);
//
//
//        User user1 = User.builder()
//                .roles(List.of(adminRole))
//                .username("admin")
//                .password(encoder.encode("111"))
//                .build();
//
//        User user2 = User.builder()
//                .roles(List.of(salesManager))
//                .username("sale")
//                .password(encoder.encode("111"))
//                .build();
//
//        userRepo.save(user1);
//        userRepo.save(user2);
//
//        List<Product> products = productRepo.findAll();
//
//        Random random=new Random();
//
//        for (int i = 0; i < random.nextInt(2,7); i++) {
//
//            Sale sale = Sale.builder()
//                    .product(products.get(0))
//                    .amount(random.nextInt(8,15))
//                    .salePrice(products.get(0).getPrice())
//                    .build();
//            saleRepo.save(sale);
//
//        }
//
//        for (int i = 0; i < random.nextInt(2,7); i++) {
//
//            Sale sale = Sale.builder()
//                    .product(products.get(1))
//                    .amount(random.nextInt(8,15))
//                    .salePrice(products.get(1).getPrice())
//                    .build();
//            saleRepo.save(sale);
//
//        }
//
//        for (int i = 0; i < random.nextInt(2,7); i++) {
//
//            Sale sale = Sale.builder()
//                    .product(products.get(2))
//                    .amount(random.nextInt(8,15))
//                    .salePrice(products.get(2).getPrice())
//                    .build();
//            saleRepo.save(sale);
//
//        }
//
//        for (int i = 0; i < random.nextInt(2,7); i++) {
//
//            Sale incomeProduct = Sale.builder()
//                    .product(products.get(3))
//                    .amount(random.nextInt(8,15))
//                    .salePrice(products.get(3).getPrice())
//                    .build();
//            saleRepo.save(incomeProduct);
//
//        }
//
//        for (int i = 0; i < random.nextInt(2,7); i++) {
//
//            Sale incomeProduct = Sale.builder()
//                    .product(products.get(4))
//                    .amount(random.nextInt(8,15))
//                    .salePrice(products.get(4).getPrice())
//                    .build();
//            saleRepo.save(incomeProduct);
//
//        }
//
//        for (int i = 0; i < random.nextInt(2,7); i++) {
//
//            Sale incomeProduct = Sale.builder()
//                    .product(products.get(5))
//                    .amount(random.nextInt(8,15))
//                    .salePrice(products.get(5).getPrice())
//                    .build();
//            saleRepo.save(incomeProduct);
//
//        }






    }
}
