package com.example.microservice3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private  Long _id;
    private String sku;
    private String productImage;
    private String productName;
    private Long subCategoryId;;
    private String subCategoryName;
    private String shortDescription;
    private String longDescription;
    private Double price;
    private Double quantity;
    private Double discountPrice;
    private Boolean active;

}
