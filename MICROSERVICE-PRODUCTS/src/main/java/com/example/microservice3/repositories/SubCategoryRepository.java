package com.example.microservice3.repositories;
import com.example.microservice3.entities.Category;
import com.example.microservice3.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
//    For Category ==> SubCategory
    List<SubCategory> findByCategory(Category category);

    SubCategory findBySubCategoryName(String subCategoryName);

//    @Query("from SubCategory s where s.subCategoryName = :query")
    @Query("from SubCategory s where s.subCategoryName like %:query%")
    List<SubCategory> findByQuery(String query, Pageable pageable);


}
