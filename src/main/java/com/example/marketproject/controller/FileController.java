package com.example.marketproject.controller;

import com.example.marketproject.entity.Attachment;
import com.example.marketproject.repo.AttachmentRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final AttachmentRepo attachmentRepo;


    @GetMapping("/show")
    public void showFile(@RequestParam UUID attachmentId, HttpServletResponse response) throws IOException {

        Attachment attachment = attachmentRepo.findById(attachmentId).get();
        response.setContentType("image/jpeg");
        response.getOutputStream().write(attachment.getContent());

    }

}
