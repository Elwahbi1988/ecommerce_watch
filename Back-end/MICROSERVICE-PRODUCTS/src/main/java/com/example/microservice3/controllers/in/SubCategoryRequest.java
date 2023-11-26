package com.example.microservice3.controllers.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequest {
    private Long categoryId;
    private String subCategoryName;

}
