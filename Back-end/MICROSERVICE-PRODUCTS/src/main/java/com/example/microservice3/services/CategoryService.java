package com.example.microservice3.services;

import com.example.microservice3.controllers.in.CategoryRequest;
import com.example.microservice3.dto.CategoryDTO;
import com.example.microservice3.dto.DeleteCategoryDTO;
import com.example.microservice3.dto.UpdateCategoryDTO;
import com.example.microservice3.entities.Category;
import com.example.microservice3.entities.SubCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    //.................................Create .................................
    Boolean createCategory(CategoryDTO categoryDTO);

    //.................................List.................................
    List<CategoryDTO> getAllCategories(int page);

    //.................................Search By Query.................................
    List<CategoryDTO> searchCategoriesByQuery(String query);

    //.......................................ID...................................
    List<CategoryDTO> getCategoryById(Long id);

    //.......................................Update...................................
    UpdateCategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    //..............................Delete..............................
    DeleteCategoryDTO deleteCategory(Long id);

    Optional<Category> getCategoryById2(Long id);

}
