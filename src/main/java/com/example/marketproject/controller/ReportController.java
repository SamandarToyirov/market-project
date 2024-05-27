package com.example.marketproject.controller;

import com.example.marketproject.projection.BalanceReportProjection;
import com.example.marketproject.projection.ProductSaleReportProjection;
import com.example.marketproject.projection.ProfitReportProjection;
import com.example.marketproject.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class ReportController {

    private final ProductRepo productRepo;

    @GetMapping("/report")
    public String getReport(Model model){

        List<ProductSaleReportProjection> productSaleReport = productRepo.getProductSaleReport();
        List<BalanceReportProjection> balanceProjectionReport = productRepo.getBalanceProjectionReport();
        List<ProfitReportProjection> eachProductProfit = productRepo.getEachProductProfit();
        model.addAttribute("saleRepors", productSaleReport);
        model.addAttribute("balanceReports", balanceProjectionReport);
        model.addAttribute("productProfits", eachProductProfit);


        return "report";
    }



}



