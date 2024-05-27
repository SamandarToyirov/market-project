package com.example.marketproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductAddReportDto {


    private UUID productId;
    private String name;

    private Integer price;
    private Integer remain;
    private Integer amount;

}
