package com.example.microservice3.controllers;

import com.example.microservice3.Roles.JwtUtils;
import com.example.microservice3.controllers.in.CategoryRequest;
import com.example.microservice3.controllers.out.CategoriesResponse;
import com.example.microservice3.controllers.out.CategoryResponse;
import com.example.microservice3.dto.CategoryDTO;
import com.example.microservice3.dto.DeleteCategoryDTO;
import com.example.microservice3.dto.UpdateCategoryDTO;
import com.example.microservice3.services.CategoryService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.example.microservice3.utils.CategoryMapper.mapCategoryRequestToCategoryDTO;


@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    private CategoryService categoryService;
    private JwtUtils jwtUtils;

    //.................................Create .................................
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {

                CategoryDTO categoryDTO = mapCategoryRequestToCategoryDTO(categoryRequest);
                Boolean isExistCategory = categoryService.createCategory(categoryDTO);
                if (isExistCategory == false) {

                    CategoryResponse categoryResponse = CategoryResponse.builder()
                            .status(201)
                            .message("Category created successfully")
                            .build();

                    return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
                } else {

                    CategoryResponse categoryResponse = CategoryResponse.builder()
                            .status(400)
                            .message("the category " + categoryRequest.getCategoryName() + " already exist")
                            .build();

                    return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
                }
    }
//    @PostMapping("/categories")
//    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest,
//                                                           @RequestHeader("Authorization") String token) {
//        try {
//            Claims claims = jwtUtils.extractClaims(token.replace("Bearer ", ""));
//            List<String> roles = (List<String>) claims.get("role");
//            if (roles != null && (roles.contains("admin") || roles.contains("manager"))) {
//                CategoryDTO categoryDTO = mapCategoryRequestToCategoryDTO(categoryRequest);
//                Boolean isExistCategory = categoryService.createCategory(categoryDTO);
//                if (!isExistCategory) {
//                    CategoryResponse categoryResponse = CategoryResponse.builder()
//                            .status(201)
//                            .message("Category created successfully")
//                            .build();
//                    return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
//                } else {
//                    CategoryResponse categoryResponse = CategoryResponse.builder()
//                            .status(400)
//                            .message("The category " + categoryRequest.getCategoryName() + " already exists")
//                            .build();
//                    return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
//                }
//            } else {
//                CategoryResponse categoryResponse = CategoryResponse.builder()
//                        .status(403)
//                        .message("Insufficient permissions to perform this action")
//                        .build();
//                return new ResponseEntity<>(categoryResponse, HttpStatus.FORBIDDEN);
//            }
//        } catch (Exception e) {
//            // Log the exception details for debugging
//            e.printStackTrace(); // Or use proper logging mechanism
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new CategoryResponse(500, "Internal server error"));
//        }
//    }


    //.................................List.................................

    @GetMapping("/categories")
    public ResponseEntity<CategoriesResponse> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int page) {
        List<CategoryDTO> categories = categoryService.getAllCategories(page);
        CategoriesResponse categoriesResponse = CategoriesResponse.builder()
                .status(201)
                .data(categories)
                .build();
        return new ResponseEntity<>(categoriesResponse, HttpStatus.OK);

    }
    //.................................Search By Query.................................

    @GetMapping(value = "/categories", params = "query")
    public ResponseEntity<CategoriesResponse> searchCategoriesByQuery(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "page", required = false) int page) {
        List<CategoryDTO> categories = categoryService.searchCategoriesByQuery(page, query);
        CategoriesResponse categoriesResponse = CategoriesResponse.builder()
                .status(201)
                .data(categories)
                .build();
        return new ResponseEntity<>(categoriesResponse, HttpStatus.OK);

    }


    //.................................Get By ID.................................

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        List<CategoryDTO> categoryDTO = categoryService.getCategoryById(id);

        if (categoryDTO != null && !categoryDTO.isEmpty()) {
            CategoriesResponse categoriesResponse = CategoriesResponse.builder()
                    .status(200)
                    .data(categoryDTO)
                    .build();
            return new ResponseEntity<>(categoriesResponse, HttpStatus.OK);
        } else {
            CategoryResponse categoryResponse = CategoryResponse.builder()
                    .status(404)
                    .message("Category Not Found")
                    .build();

            return new ResponseEntity<>(categoryResponse, HttpStatus.NOT_FOUND);
        }
    }
    //.................................Update .................................

    @PatchMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {

        UpdateCategoryDTO updateCategoryDTO = categoryService.updateCategory(id, categoryDTO);

        if (updateCategoryDTO.isUpdated()) {
            CategoryResponse response = CategoryResponse.builder()
                    .status(200)
                    .message("Category updated successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (updateCategoryDTO.isForBidden()) {
            CategoryResponse response = CategoryResponse.builder()
                    .status(403)
                    .message("You don't have enough privilege")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if (updateCategoryDTO.isNotFound()) {
            CategoryResponse response = CategoryResponse.builder()
                    .status(404)
                    .message("Invalid category ID")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (updateCategoryDTO.isNoContent()) {
            return new ResponseEntity<>("Optional", HttpStatus.NO_CONTENT);
        }

        return null;
    }

    //..............................Delete..............................

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id) {
        DeleteCategoryDTO deleteCategoryDTO = categoryService.deleteCategory(id);

        if (deleteCategoryDTO.isDeleted()) {
            CategoryResponse response = CategoryResponse.builder()
                    .status(200)
                    .message("Category deleted successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (deleteCategoryDTO.isBadRequest()) {
            CategoryResponse response = CategoryResponse.builder()
                    .status(400)
                    .message("Subcategories attached, cannot delete this category")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (deleteCategoryDTO.isForBidden()) {
            CategoryResponse response = CategoryResponse.builder()
                    .status(403)
                    .message("You don't have enough privilege")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if (deleteCategoryDTO.isNotFound()) {
            CategoryResponse response = CategoryResponse.builder()
                    .status(404)
                    .message("Invalid category id")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (deleteCategoryDTO.isNoContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return null;
    }


}



