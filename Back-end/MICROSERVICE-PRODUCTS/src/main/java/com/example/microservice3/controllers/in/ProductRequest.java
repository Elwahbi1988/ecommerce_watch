package com.example.microservice3.controllers.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String sku;
    private String productImage;
    private String productName;
    private Long subCategoryId;
    private String shortDescription;
    private String longDescription;
    private Double price;
    private Double quantity;
    private Double discountPrice;

}
