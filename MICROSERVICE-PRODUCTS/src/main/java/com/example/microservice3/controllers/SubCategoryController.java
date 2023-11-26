package com.example.microservice3.controllers;

import com.example.microservice3.controllers.in.SubCategoryRequest;
import com.example.microservice3.controllers.out.SubCategoryResponse;
import com.example.microservice3.controllers.out.SubCategoriesResponse;
import com.example.microservice3.dto.*;
import com.example.microservice3.services.SubCategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.microservice3.utils.SubCategoryMapper.mapSubCategoryRequestToSubCategoryDTO;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;


    //.................................Create .................................
    @PostMapping("/subcategories")
    public ResponseEntity<SubCategoryResponse> createSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        SubCategoryDTO subCategoryDTO = mapSubCategoryRequestToSubCategoryDTO(subCategoryRequest);
        Boolean isExistCategory = subCategoryService.createSubCategory(subCategoryDTO);
        if (isExistCategory == false) {

            SubCategoryResponse subCategoryResponse = SubCategoryResponse.builder()
                    .status(201)
                    .message("SubCategory created successfully")
                    .build();

            return new ResponseEntity<>(subCategoryResponse, HttpStatus.CREATED);

        } else {

            SubCategoryResponse subCategoryResponse = SubCategoryResponse.builder()
                    .status(400)
                    .message("the subCategory " + subCategoryRequest.getSubCategoryName() + " already exist")
                    .build();

            return new ResponseEntity<>(subCategoryResponse, HttpStatus.BAD_REQUEST);
        }

    }

    //.................................List.................................

    @GetMapping("/subcategories")
    public ResponseEntity<SubCategoriesResponse> getAllSubCategories(
            @RequestParam(name = "page", defaultValue = "0") int page) {
        List<SubCategoryDTO> subCategories = subCategoryService.getAllSubCategories(page);
        SubCategoriesResponse subCategoriesResponse = SubCategoriesResponse.builder()
                .status(200)
                .data(subCategories)
                .build();
        return new ResponseEntity<>(subCategoriesResponse, HttpStatus.OK);

    }
    //.................................Search By Query.................................

    @GetMapping(value = "/subcategories", params = "query")
    public ResponseEntity<SubCategoriesResponse> searchSubCategoriesByQuery(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        List<SubCategoryDTO> subCategories = subCategoryService.searchSubCategoriesByQuery(page, query);
        SubCategoriesResponse subCategoriesResponse = SubCategoriesResponse.builder()
                .status(200)
                .data(subCategories)
                .build();
        return new ResponseEntity<>(subCategoriesResponse, HttpStatus.OK);
    }

    //.................................Get By ID.................................

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<?> getSubCategoryById(@PathVariable Long id) {
        List<SubCategoryDTO> subCategoryDTO = subCategoryService.getSubCategoryById(id);

        if (subCategoryDTO != null && !subCategoryDTO.isEmpty()) {
            SubCategoriesResponse subCategoriesResponse = SubCategoriesResponse.builder()
                    .status(200)
                    .data(subCategoryDTO)
                    .build();
            return new ResponseEntity<>(subCategoriesResponse, HttpStatus.OK);
        } else {
            SubCategoryResponse subCategoryResponse = SubCategoryResponse.builder()
                    .status(404)
                    .message("SubCategory Not Found")
                    .build();

            return new ResponseEntity<>(subCategoryResponse, HttpStatus.NOT_FOUND);
        }
    }
    //.................................Update .................................

    @PatchMapping("/subcategories/{id}")
    public ResponseEntity<?> updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryDTO subCategoryDTO) {

        UpdateSubCategoryDTO updateSubCategoryDTO = subCategoryService.updateSubCategory(id, subCategoryDTO);

        if (updateSubCategoryDTO.isUpdated()) {
            SubCategoryResponse response = SubCategoryResponse.builder()
                    .status(200)
                    .message("SubCategory updated successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (updateSubCategoryDTO.isForBidden()) {
            SubCategoryResponse response = SubCategoryResponse.builder()
                    .status(403)
                    .message("You don't have enough privilege")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if (updateSubCategoryDTO.isNotFound()) {
            SubCategoryResponse response = SubCategoryResponse.builder()
                    .status(404)
                    .message("Invalid SubCategory ID")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (updateSubCategoryDTO.isNoContent()) {
            return new ResponseEntity<>("Optional", HttpStatus.NO_CONTENT);
        }

        return null;
    }

    //..............................Delete..............................
    @DeleteMapping("/subcategories/{id}")
    public ResponseEntity<SubCategoryResponse> deleteSubCategory(@PathVariable Long id) {
        DeleteSubCategoryDTO deleteSubCategoryDTO = subCategoryService.deleteSubCategory(id);

        if (deleteSubCategoryDTO.isDeleted()) {
            SubCategoryResponse response = SubCategoryResponse.builder()
                    .status(200)
                    .message("SubCategory deleted successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (deleteSubCategoryDTO.isBadRequest()) {
            SubCategoryResponse response = SubCategoryResponse.builder()
                    .status(400)
                    .message("Products attached, cannot delete this SubCategory")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (deleteSubCategoryDTO.isForbidden()) {
            SubCategoryResponse response = SubCategoryResponse.builder()
                    .status(403)
                    .message("You don't have enough privilege")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if (deleteSubCategoryDTO.isNotFound()) {
            SubCategoryResponse response = SubCategoryResponse.builder()
                    .status(404)
                    .message("Invalid category id")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (deleteSubCategoryDTO.isNoContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return null;
    }


}
