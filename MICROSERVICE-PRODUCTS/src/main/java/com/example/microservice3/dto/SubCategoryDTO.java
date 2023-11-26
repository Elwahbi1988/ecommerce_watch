package com.example.microservice3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryDTO {
    private  Long _id;
    private Long categoryId;
    private String subCategoryName;
    private String categoryName;
    private Boolean active;
}
