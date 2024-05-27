package com.example.marketproject.entity;

import com.example.marketproject.dto.ProductAddReportDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Basket {

    List<ProductAddReportDto> reportDtoList=new ArrayList<>();

}
