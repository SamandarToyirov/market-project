package com.example.marketproject.projection;

import java.util.UUID;

public interface ProductAddReportProjection {

    UUID getId();
    String getName();
    Integer getPrice();
    Integer getRemain();
    Integer getAmount();


}
