package com.example.microservice3.utils;

import com.example.microservice3.controllers.in.CategoryRequest;
import com.example.microservice3.controllers.in.SubCategoryRequest;
import com.example.microservice3.dto.CategoryDTO;
import com.example.microservice3.dto.SubCategoryDTO;
import com.example.microservice3.entities.Category;
import com.example.microservice3.entities.SubCategory;
import org.modelmapper.ModelMapper;

public class SubCategoryMapper {

    public static SubCategoryDTO mapSubCategoryRequestToSubCategoryDTO(SubCategoryRequest subCategoryRequest) {

        return SubCategoryDTO.builder()
                .subCategoryName(subCategoryRequest.getSubCategoryName())
                .categoryId(subCategoryRequest.getCategoryId())
                .build();
    }

//    public static SubCategoryDTO mapSubCategoryToSubCategoryDTO(SubCategory subCategory) {
//
//        return SubCategoryDTO.builder()
//                ._id(subCategory.getId())
//                .subCategoryName(subCategory.getSubCategoryName())
////                .CategoryId(subCategoryRequest.getSubCategoryId()
//                .active(subCategory.getActive())
//                .build();
//    }

    public static SubCategoryDTO mapSubCategoryToSubCategoryDTO(SubCategory subCategory) {
        SubCategoryDTO subCategoryDTO = SubCategoryDTO.builder()
                ._id(subCategory.getId())
                .subCategoryName(subCategory.getSubCategoryName())
                .active(subCategory.getActive())
                .build();

        Category category = subCategory.getCategory();
        if (category != null) {
            subCategoryDTO.setCategoryId(category.getId());
            subCategoryDTO.setCategoryName(category.getCategoryName());
        }

        return subCategoryDTO;
    }

}
