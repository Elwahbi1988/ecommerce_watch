package com.example.microservice3.repositories;

import com.example.microservice3.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

     Category findByCategoryName(String categoryName);
     @Query("from Category c where c.categoryName = :query")
     List<Category> findByQuery(String query,Pageable pageable);
}
