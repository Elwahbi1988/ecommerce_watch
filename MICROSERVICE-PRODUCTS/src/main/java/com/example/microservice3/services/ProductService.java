package com.example.microservice3.services;

import com.example.microservice3.dto.*;

import java.util.List;

public interface ProductService {
//.................................Create.................................

    Boolean createProduct(ProductDTO productDTO);

    //.................................List.................................
    List<ProductDTO> getAllProduct(int page);

    //.................................Search By Query.................................
    List<ProductDTO> searchProductsByQuery(int page, String query);
    //.......................................ID...................................
    List<ProductDTO> getProductById(Long id);

    //.......................................Update...................................
    UpdateProductDTO updateProduct(Long id, ProductDTO productDTO);
    //..............................Delete..............................
    DeleteProductDTO deleteProduct(Long id);
}
