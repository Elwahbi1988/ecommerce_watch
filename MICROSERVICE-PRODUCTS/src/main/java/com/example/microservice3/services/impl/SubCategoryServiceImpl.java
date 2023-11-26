package com.example.microservice3.services.impl;

import com.example.microservice3.dto.*;
//import com.example.microservice3.dto.UpdateSubCategoryDTO;
import com.example.microservice3.entities.Category;
import com.example.microservice3.entities.Product;
import com.example.microservice3.entities.SubCategory;
import com.example.microservice3.repositories.CategoryRepository;
import com.example.microservice3.repositories.ProductRepository;
import com.example.microservice3.repositories.SubCategoryRepository;
import com.example.microservice3.services.SubCategoryService;
import com.example.microservice3.utils.SubCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired

    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

@Override
public Boolean createSubCategory(SubCategoryDTO subCategoryDTO) {
    SubCategory existSubCategory = subCategoryRepository.findBySubCategoryName(subCategoryDTO.getSubCategoryName());
    if (existSubCategory == null) {
        SubCategory subCategoryEntity = new SubCategory();
        subCategoryEntity.setSubCategoryName(subCategoryDTO.getSubCategoryName());
        subCategoryEntity.setActive(false);

        // Set the Category for the SubCategory
        Long categoryId = subCategoryDTO.getCategoryId(); // Assuming this contains the Category ID
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category != null) {
            subCategoryEntity.setCategory(category);
            subCategoryRepository.save(subCategoryEntity);
            return false;
        } else {
            // Handle the case where the Category with the provided ID was not found
            return true; // Return true to indicate that the operation was not successful
        }
    } else {
        return true;
    }
}



    //.................................List.................................
//    @Override
//    public List<SubCategoryDTO> getAllSubCategories(int page) {
//
//
//        PageRequest pageable = PageRequest.of(page, 10);
//
//        return subCategoryRepository.findAll(pageable).stream()
//                .map(b -> SubCategoryMapper.mapSubCategoryToSubCategoryDTO(b))
//                .collect(Collectors.toList()) ;
//    }

    @Override
    public List<SubCategoryDTO> getAllSubCategories(int page) {
        PageRequest pageable = PageRequest.of(page, 10);

        return subCategoryRepository.findAll(pageable).stream()
                .map(subCategory -> {
                    SubCategoryDTO subCategoryDTO = SubCategoryMapper.mapSubCategoryToSubCategoryDTO(subCategory);
                    Category category = subCategory.getCategory();

                    if (category != null) {
                        subCategoryDTO.setCategoryId(category.getId());
                        subCategoryDTO.setCategoryName(category.getCategoryName());
                    }

                    return subCategoryDTO;
                })
                .collect(Collectors.toList());
    }


    //.................................Search By Query.................................
//    @Override
//    public List<SubCategoryDTO> searchSubCategoriesByQuery(int page, String query) {
//
//
//        PageRequest pageable = PageRequest.of(page, 10);
//
//        return subCategoryRepository.findByQuery(query, pageable).stream()
//                .map(b -> SubCategoryMapper.mapSubCategoryToSubCategoryDTO(b))
//                .collect(Collectors.toList()) ;
//    }
    @Override
    public List<SubCategoryDTO> searchSubCategoriesByQuery(int page, String query) {

        PageRequest pageable = PageRequest.of(page, 10);

        return subCategoryRepository.findByQuery(query, pageable).stream()
                .map(subCategory -> {
                    SubCategoryDTO subCategoryDTO = SubCategoryMapper.mapSubCategoryToSubCategoryDTO(subCategory);
                    Category category = subCategory.getCategory();

                    if (category != null) {
                        subCategoryDTO.setCategoryId(category.getId());
                        subCategoryDTO.setCategoryName(category.getCategoryName());
                    }

                    return subCategoryDTO;
                })
                .collect(Collectors.toList());
    }


    //.......................................ID...................................

    @Override
    public List<SubCategoryDTO> getSubCategoryById(Long id) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);

        if (subCategory.isPresent()) {
            SubCategoryDTO subCategoryDTO = SubCategoryMapper.mapSubCategoryToSubCategoryDTO(subCategory.get());
            Category category = subCategory.get().getCategory();

            if (category != null) {
                subCategoryDTO.setCategoryId(category.getId());
                subCategoryDTO.setCategoryName(category.getCategoryName());
            }

            return Collections.singletonList(subCategoryDTO);
        }

        return Collections.emptyList();
    }



    //..............................Update..............................

    @Override
    public UpdateSubCategoryDTO updateSubCategory(Long id, SubCategoryDTO subCategoryDTO) {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
        UpdateSubCategoryDTO updateSubCategoryDTO = new UpdateSubCategoryDTO();

        if (subCategoryOptional.isPresent()) {
            SubCategory existingSubCategory = subCategoryOptional.get();
            // CategoryName PostMan != CategoryName Repo || active PostMan != active Repo ----> update
            // CategoryName PostMan = CategoryName Repo && active PostMan = active Repo ----> Not update (204)
            // Check if the category name is being updated
            if (!subCategoryDTO.getSubCategoryName().equals(existingSubCategory.getSubCategoryName())
                    || !subCategoryDTO.getActive().equals(existingSubCategory.getActive())
            ) {
                updateSubCategoryDTO.setUpdated(true);
                existingSubCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
                existingSubCategory.setActive(subCategoryDTO.getActive());
//                Optional<Category> categoryEntity = categoryRepository.findById(subCategoryDTO.getCategoryId());
//               Method instantiation
                Category existingCategory = new Category();
                existingCategory.setId(subCategoryDTO.getCategoryId());
                existingSubCategory.setCategory(existingCategory);

                subCategoryRepository.save(existingSubCategory);
                return updateSubCategoryDTO;
            } else if(subCategoryDTO.getSubCategoryName().equals(existingSubCategory.getSubCategoryName())
                    && subCategoryDTO.getActive().equals(existingSubCategory.getActive())
            ) {

                updateSubCategoryDTO.setNoContent(true);
                return updateSubCategoryDTO;
            }
        } else {
            // Category not found
            updateSubCategoryDTO.setNotFound(true);
            return updateSubCategoryDTO;
        }
        return updateSubCategoryDTO;
    }




    //..............................Delete..............................
//
//    public DeleteSubCategoryDTO deleteSubCategory(Long id) {
//        DeleteSubCategoryDTO deleteSubCategoryDTO = new DeleteSubCategoryDTO();
//        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
//
//        if (subCategoryOptional.isPresent()) {
//
//            SubCategory subCategory = subCategoryOptional.get();
//
//            List<Product> attachedProducts = productRepository.findBySubCategory(subCategory);
//
//            if (attachedProducts.isEmpty()) {
//                subCategoryRepository.delete(subCategory);
//                deleteSubCategoryDTO.setDeleted(true);
//            } else {
//                // Attached subcategories found, cannot delete the category
//                deleteSubCategoryDTO.setBadRequest(true);
//            }
//        } else {
//            // Category not found
//            deleteSubCategoryDTO.setNotFound(true);
//        }
//
//        return deleteSubCategoryDTO;
//    }
    public DeleteSubCategoryDTO deleteSubCategory(Long id) {
        DeleteSubCategoryDTO deleteSubCategoryDTO = new DeleteSubCategoryDTO();
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);

        if (subCategoryOptional.isPresent()) {
            SubCategory subCategory = subCategoryOptional.get();

            List<Product> attachedProducts = productRepository.findBySubCategory(subCategory);

            if (attachedProducts.isEmpty()) {
                subCategoryRepository.delete(subCategory);
                deleteSubCategoryDTO.setDeleted(true);
            } else {
                // Attached products found, cannot delete the subcategory
                deleteSubCategoryDTO.setBadRequest(true);
            }
        } else {
            // Subcategory not found
            deleteSubCategoryDTO.setNotFound(true);
        }

        return deleteSubCategoryDTO;
    }

}
