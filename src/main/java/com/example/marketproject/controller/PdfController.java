package com.example.marketproject.controller;

import com.example.marketproject.entity.Basket;
import com.example.marketproject.service.PDFService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
public class PdfController {

    private final PDFService pdfService;

    @GetMapping("/pdf/download")
    public void doenloadPdf(HttpServletResponse response, HttpSession session) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"cheque.pdf\"");
        Basket basket = (Basket) session.getAttribute("basket");
        ByteArrayOutputStream byteArrayOutputStream = pdfService.genPdf(basket);

        response.getOutputStream().write(byteArrayOutputStream.toByteArray());
        response.getOutputStream().flush();


    }

}
