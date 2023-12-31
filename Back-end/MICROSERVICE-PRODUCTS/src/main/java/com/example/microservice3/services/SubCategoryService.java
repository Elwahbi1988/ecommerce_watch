package com.example.microservice3.services;

import com.example.microservice3.dto.*;

import java.util.List;

public interface SubCategoryService {
    //.................................Create.................................

        Boolean createSubCategory(SubCategoryDTO subCategoryDTO);

        //.................................List.................................
        List<SubCategoryDTO> getAllSubCategories(int page);

        //.................................Search By Query.................................
        List<SubCategoryDTO> searchSubCategoriesByQuery(int page, String query);
        //.......................................ID...................................
        List<SubCategoryDTO> getSubCategoryById(Long id);

        //.......................................Update...................................
        UpdateSubCategoryDTO updateSubCategory(Long id, SubCategoryDTO subCategoryDTO);

    //    //..............................Delete..............................
        DeleteSubCategoryDTO deleteSubCategory(Long id);

}
