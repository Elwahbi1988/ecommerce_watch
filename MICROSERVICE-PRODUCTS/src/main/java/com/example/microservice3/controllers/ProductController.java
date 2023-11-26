package com.example.microservice3.controllers;

import com.example.microservice3.controllers.in.ProductRequest;
import com.example.microservice3.controllers.in.SubCategoryRequest;
import com.example.microservice3.controllers.out.ProductResponse;
import com.example.microservice3.controllers.out.ProductsResponse;
import com.example.microservice3.controllers.out.SubCategoriesResponse;
import com.example.microservice3.controllers.out.ProductResponse;
import com.example.microservice3.dto.*;
import com.example.microservice3.services.ProductService;
import com.example.microservice3.services.SubCategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.microservice3.utils.ProductMapper.mapProductRequestToProductDTO;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;


    //.................................Create .................................

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductDTO productDTO = mapProductRequestToProductDTO(productRequest);
        Boolean isProductCreated = productService.createProduct(productDTO);

        if (isProductCreated == false) {
            ProductResponse productResponse = ProductResponse.builder()
                   // .status(201)
                    //.message("Product created successfully")
                    .build();
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } else {
            ProductResponse productResponse = ProductResponse.builder()
                    //.status(400)
                    //.message("Product already exists with the name: " + productRequest.getProductName() + " and SKU: " + productRequest.getSku())
                    .build();
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        }
    }


    //.................................List.................................

    @GetMapping("/products")
    public ResponseEntity<ProductsResponse> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page) {
        List<ProductDTO> products = productService.getAllProduct(page);
        ProductsResponse productsResponse = ProductsResponse.builder()
                //.status(200)
                .data(products)
                .build();
        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    //.................................Search By Query.................................

    @GetMapping(value = "/products", params = "query")
    public ResponseEntity<ProductsResponse> searchProductsByQuery(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        List<ProductDTO> products = productService.searchProductsByQuery(page, query);
        ProductsResponse productsResponse = ProductsResponse.builder()
               // .status(200)
                .data(products)
                .build();
        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    //.................................Get By ID.................................

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        List<ProductDTO> productDTO = productService.getProductById(id);

        if (productDTO != null && !productDTO.isEmpty()) {
            ProductsResponse productsResponse = ProductsResponse.builder()
                    //.status(200)
                    .data(productDTO)
                    .build();
            return new ResponseEntity<>(productsResponse, HttpStatus.OK);
        } else {
            ProductResponse productResponse = ProductResponse.builder()
                    //.status(404)
                    .message("Product Not Found")
                    .build();

            return new ResponseEntity<>(productResponse, HttpStatus.NOT_FOUND);
        }
    }
    //.................................Update .................................

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        UpdateProductDTO updateProductDTO = productService.updateProduct(id, productDTO);

        if (updateProductDTO.isUpdated()) {
            ProductResponse response = ProductResponse.builder()
                    //.status(200)
                    //.message("Product updated successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (updateProductDTO.isBadRequest()) {
            ProductResponse productResponse = ProductResponse.builder()
                    //.status(400)
                   // .message("The product name should be unique")
                    .build();
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        } else if (updateProductDTO.isForbidden()) {
            ProductResponse response = ProductResponse.builder()
                    //.status(403)
                  //  .message("You don't have enough privilege")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if (updateProductDTO.isNotFound()) {
            ProductResponse response = ProductResponse.builder()
                    //.status(404)
                   // .message("Product not found, invalid Product ID")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (updateProductDTO.isNoContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }


        //..............................Delete..............................

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
        DeleteProductDTO deleteProductDTO = productService.deleteProduct(id);

        if (deleteProductDTO.isDeleted()) {
            ProductResponse response = ProductResponse.builder()
                   // .status(200)
                    //.message("Product deleted successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (deleteProductDTO.isForbidden()) {
            ProductResponse response = ProductResponse.builder()
                    //.status(403)
                    //.message("You don't have enough privilege")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if (deleteProductDTO.isNotFound()) {
            ProductResponse response = ProductResponse.builder()
                    //.status(404)
                    //.message("Invalid product id")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        return null;
    }


}
