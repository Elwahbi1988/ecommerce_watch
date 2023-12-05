package com.example.microservice3.repositories;

import com.example.microservice3.entities.Category;
import com.example.microservice3.entities.Product;
import com.example.microservice3.entities.SubCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySubCategory(SubCategory subCategory);
    Product findByProductName(String productName);
    Product findBySku(String sku);

    Product findByProductNameAndIdNot(String productName,Long id);
    //    @Query("from SubCategory s where s.subCategoryName = :query")
    @Query("from Product s where s.productName like %:query%" )
    List<Product> findByQuery(String query, Pageable pageable);




}
