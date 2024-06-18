package com.example.marketproject.service;

import com.example.marketproject.dto.ProductAddReportDto;
import com.example.marketproject.entity.Basket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class PDFService {

        @SneakyThrows
        public ByteArrayOutputStream genPdf(Basket basket){

                Document document = new Document(PageSize.A4);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, byteArrayOutputStream);
                document.open();

                Font font = new Font(Font.HELVETICA, 12, Font.NORMAL);

                Paragraph title = new Paragraph("PDP CHEQUE", new Font(Font.HELVETICA, 20, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                for (ProductAddReportDto product : basket.getReportDtoList()) {
                        document.add(new Paragraph(genChequeStr(product), font));
                }


                String qrContent = "Cheque Number: " + 10 + "\n" +
                        "Payee Name: PDP" +
                        "Amount: 100" ;
                ByteArrayOutputStream qrOut = generateQRCodeImage(qrContent, 100, 100);

                int sum=0;

                for (ProductAddReportDto item : basket.getReportDtoList()) {

                        sum+=item.getAmount()*item.getPrice();

                }

                Image qrImage = Image.getInstance(qrOut.toByteArray());
                qrImage.setAlignment(Element.ALIGN_RIGHT);
                document.add(new Paragraph("______________________________________________________________________"));
                document.add(new Paragraph("TOTAL: "+sum+" $"));
                document.add(qrImage);

                document.close();
                return byteArrayOutputStream;

        }

        @SneakyThrows
        private ByteArrayOutputStream generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

                ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
                return pngOutputStream;
        }

        private String genChequeStr(ProductAddReportDto product) {


                return """
                        
                   %s        Narxi: %s     Soni: %d       Umumiy: %s     
                        
                        """.formatted(product.getName(), product.getPrice()+" $", product.getAmount(),
                        product.getAmount()*product.getPrice()+" $");

        }

}
