package com.example.microservice3.services.impl;

import com.example.microservice3.dto.CategoryDTO;
import com.example.microservice3.dto.DeleteCategoryDTO;
import com.example.microservice3.dto.UpdateCategoryDTO;
import com.example.microservice3.entities.Category;
import com.example.microservice3.entities.SubCategory;
import com.example.microservice3.repositories.CategoryRepository;
import com.example.microservice3.repositories.SubCategoryRepository;
import com.example.microservice3.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.microservice3.utils.CategoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;


    //.................................Create .................................

    @Override
    public Boolean createCategory(CategoryDTO categoryDTO) {
        Category existCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (existCategory == null) {

            Category categoryEntity = new Category();
            categoryEntity.setCategoryName(categoryDTO.getCategoryName());
            categoryEntity.setActive(false);
            categoryRepository.save(categoryEntity);
            return false;

        }else {
            return true;
        }
    }

    //.................................List.................................
   @Override
   public List<CategoryDTO> getAllCategories(int page) {


       PageRequest pageable = PageRequest.of(page, 10);

        return categoryRepository.findAll(pageable).stream()
                .map(b -> CategoryMapper.mapCategoryToCategoryDTO(b))
                .collect(Collectors.toList()) ;
    }
    //.................................Search By Query.................................
    @Override
    public List<CategoryDTO> searchCategoriesByQuery(int page, String query) {


        PageRequest pageable = PageRequest.of(page, 10);

        return categoryRepository.findByQuery(query, pageable).stream()
                .map(b -> CategoryMapper.mapCategoryToCategoryDTO(b))
                .collect(Collectors.toList()) ;
    }

    //.......................................ID...................................
    @Override
    public  List<CategoryDTO> getCategoryById(Long id){
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isPresent())
        { return categoryRepository.findById(id).stream()
                .map(b -> CategoryMapper.mapCategoryToCategoryDTO(b))
                .collect(Collectors.toList()) ;
        }

        return null;
    }


    //..............................Update..............................

    @Override
    public UpdateCategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO();

        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();
            // CategoryName PostMan != CategoryName Repo || active PostMan != active Repo ----> update
            // CategoryName PostMan = CategoryName Repo && active PostMan = active Repo ----> Not update (204)
            // Check if the category name is being updated
            if (!categoryDTO.getCategoryName().equals(existingCategory.getCategoryName())
                    || !categoryDTO.getActive().equals(existingCategory.getActive())
            ) {
                updateCategoryDTO.setUpdated(true);
                existingCategory.setCategoryName(categoryDTO.getCategoryName());
                existingCategory.setActive(categoryDTO.getActive());
                categoryRepository.save(existingCategory);
                return updateCategoryDTO;
            } else if(categoryDTO.getCategoryName().equals(existingCategory.getCategoryName())
                    && categoryDTO.getActive().equals(existingCategory.getActive())
            ) {

                updateCategoryDTO.setNoContent(true);
                return updateCategoryDTO;
            }
        } else {
            // Category not found
            updateCategoryDTO.setNotFound(true);
            return updateCategoryDTO;
        }
        return updateCategoryDTO;
    }




    //..............................Delete..............................

    public DeleteCategoryDTO deleteCategory(Long id) {
        DeleteCategoryDTO deleteCategoryDTO = new DeleteCategoryDTO();
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {

            Category category = categoryOptional.get();

            List<SubCategory> attachedSubcategories = subCategoryRepository.findByCategory(category);

            if (attachedSubcategories.isEmpty()) {
                categoryRepository.delete(category);
                deleteCategoryDTO.setDeleted(true);
            } else {
                // Attached subcategories found, cannot delete the category
                deleteCategoryDTO.setBadRequest(true);
            }
        } else {
            // Category not found
            deleteCategoryDTO.setNotFound(true);
        }

        return deleteCategoryDTO;
    }

}
