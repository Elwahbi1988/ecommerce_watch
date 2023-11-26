package com.example.microservice3.utils;

import com.example.microservice3.controllers.in.CategoryRequest;
import com.example.microservice3.dto.UpdateCategoryDTO;
import com.example.microservice3.entities.Category;
import com.example.microservice3.dto.CategoryDTO;

public class CategoryMapper {


        public static CategoryDTO mapCategoryRequestToCategoryDTO(CategoryRequest categoryRequest) {

                return CategoryDTO.builder()
                        .categoryName(categoryRequest.getCategoryName())
                        .build();
        }

        public static CategoryDTO mapCategoryToCategoryDTO(Category category) {

                return CategoryDTO.builder()
                        ._id(category.getId())
                        .categoryName(category.getCategoryName())
                        .active(category.getActive())
                        .build();
        }



}
