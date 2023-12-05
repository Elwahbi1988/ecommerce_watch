package com.example.microservice3.services.impl;

import com.example.microservice3.dto.DeleteProductDTO;
import com.example.microservice3.dto.ProductDTO;
import com.example.microservice3.dto.UpdateProductDTO;
import com.example.microservice3.entities.Product;
import com.example.microservice3.entities.SubCategory;
import com.example.microservice3.repositories.ProductRepository;
import com.example.microservice3.repositories.SubCategoryRepository;
import com.example.microservice3.services.ProductService;
import com.example.microservice3.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Boolean createProduct(ProductDTO productDTO) {
        Product existByProductName = productRepository.findByProductName(productDTO.getProductName());
        Product existBySku = productRepository.findBySku(productDTO.getSku());

        // Check for uniqueness of product name and SKU
        if (existByProductName != null || existBySku != null) {
            return true; // Product name or SKU already exists
        }

        Product productEntity = new Product();
        productEntity.setSku(productDTO.getSku());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductImage(productDTO.getProductImage());
        productEntity.setShortDescription(productDTO.getShortDescription());
        productEntity.setLongDescription(productDTO.getLongDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setDiscountPrice(productDTO.getDiscountPrice());
        productEntity.setQuantity(productDTO.getQuantity());
        productEntity.setActive(false);


        Long subCategoryId = productDTO.getSubCategoryId();
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElse(null);

        if (subCategory != null) {
            productEntity.setSubCategory(subCategory);
            productRepository.save(productEntity);
            return false; // Product created successfully
        } else {
            return true; // Subcategory doesn't exist
        }

    }


    //.................................List.................................

    @Override
    public List<ProductDTO> getAllProduct(int page) {
        PageRequest pageable = PageRequest.of(page, 1000);

        List<ProductDTO> products = productRepository.findAll(pageable)
                .stream()
                .map(product -> {
                    ProductDTO productEntity = ProductMapper.mapProductToProductDTO(product);
                    SubCategory subCategory = product.getSubCategory();

                    if (subCategory != null) {
                        productEntity.setSubCategoryId(subCategory.getId());
                        productEntity.setSubCategoryName(subCategory.getSubCategoryName());


//                    Category category = subCategory.getCategory();
//                    if (category != null) {
//                        productDTO.setCategoryName(category.getCategoryName());
//                    }
                    }

                    return productEntity;
                })
                .collect(Collectors.toList());

        return products;
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
    public List<ProductDTO> searchProductsByQuery(int page, String query) {

        PageRequest pageable = PageRequest.of(page, 10);

        return productRepository.findByQuery(query, pageable).stream()
                .map(product -> {
                    ProductDTO productDTO = ProductMapper.mapProductToProductDTO(product);
                    SubCategory subCategory = product.getSubCategory();

                    if (subCategory != null) {
                        productDTO.setSubCategoryId(subCategory.getId());
                        productDTO.setSubCategoryName(subCategory.getSubCategoryName());
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());
    }


    //.......................................ID...................................

    @Override
    public List<ProductDTO> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            ProductDTO productDTO = ProductMapper.mapProductToProductDTO(product.get());
            SubCategory subCategory = product.get().getSubCategory();

            if (subCategory != null) {
                productDTO.setSubCategoryId(subCategory.getId());
                productDTO.setSubCategoryName(subCategory.getSubCategoryName());
            }

            return Collections.singletonList(productDTO);
        }

        return Collections.emptyList();
    }


    //..............................Update..............................

    //    @Override
//    public UpdateProductDTO updateProduct(Long id, ProductDTO productDTO) {
//        Optional<Product> productOptional = productRepository.findById(id);
//        UpdateProductDTO updateProductDTO = new UpdateProductDTO();
//
//        if (productOptional.isPresent()) {
//            Product existingProduct = productOptional.get();
//            if (!productDTO.getProductName().equals(existingProduct.getProductName())
//                    || !productDTO.getActive().equals(existingProduct.getActive())
//            ) {
//                updateProductDTO.setUpdated(true);
//                existingProduct.setProductName(productDTO.getProductName());
//                existingProduct.setActive(productDTO.getActive());
//
//                Optional<SubCategory> subCategoryEntity = subCategoryRepository.findById(productDTO.getSubCategoryId());
//                SubCategory existingSubCategory = new SubCategory();
////               existingSubCategory.setId(ProductDTO.getSubCategoryId());
//                existingProduct.setSubCategory(existingSubCategory);
//
//                subCategoryRepository.save(existingSubCategory);
//                return updateProductDTO;
//            } else if(productDTO.getSubCategoryName().equals(existingProduct.getProductName())
//                    && productDTO.getActive().equals(existingProduct.getActive())
//            ) {
//
//                updateProductDTO.setNoContent(true);
//                return updateProductDTO;
//            }
//        } else {
//            // Category not found
//            updateProductDTO.setNotFound(true);
//            return updateProductDTO;
//        }
//        return updateProductDTO;
//    }
    @Override
    public UpdateProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        UpdateProductDTO updateProductDTO = new UpdateProductDTO();

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            if (!productDTO.getProductName().equals(existingProduct.getProductName())
                    || !Objects.equals(productDTO.getActive(), existingProduct.getActive())
                    || !Objects.equals(productDTO.getSubCategoryId(), existingProduct.getSubCategory().getId())
                    || !Objects.equals(productDTO.getSku(), existingProduct.getSku())

            ) {
                Product existByName = productRepository.findByProductNameAndIdNot(productDTO.getProductName(),id);
                if (existByName != null ) {
                    updateProductDTO.setBadRequest(true);
                    return updateProductDTO; // Product name is not unique
                }

                existingProduct.setProductName(productDTO.getProductName());
                existingProduct.setActive(productDTO.getActive());
                existingProduct.setSku(productDTO.getSku());
                existingProduct.setProductImage(productDTO.getProductImage());
                existingProduct.setQuantity(productDTO.getQuantity());
                existingProduct.setShortDescription(productDTO.getShortDescription());


                Optional<SubCategory> subCategoryEntity = subCategoryRepository.findById(productDTO.getSubCategoryId());
                if (subCategoryEntity.isPresent()) {
                    existingProduct.setSubCategory(subCategoryEntity.get());
                } else {
                    updateProductDTO.setNotFound(true);
                    return updateProductDTO; // Subcategory not found
                }

                productRepository.save(existingProduct);
                updateProductDTO.setUpdated(true);
            } else {
                updateProductDTO.setNoContent(true);
            }
        } else {
            updateProductDTO.setNotFound(true);
        }

        return updateProductDTO;
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
//    public DeleteProductDTO deleteProduct(Long id) {
//        DeleteProductDTO deleteProductDTO = new DeleteProductDTO();
//        Optional<Product> productOptional = productRepository.findById(id);
//
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//
//            productRepository.delete(product);
//            deleteProductDTO.setDeleted(true);
//        } else {
//            // Product not found
//            deleteProductDTO.setNotFound(true);
//        }
//
//        return deleteProductDTO;
//    }
    public DeleteProductDTO deleteProduct(Long id) {
        DeleteProductDTO deleteProductDTO = new DeleteProductDTO();
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
            deleteProductDTO.setDeleted(true);
        } else {
            // Product not found
            deleteProductDTO.setNotFound(true);
        }

        return deleteProductDTO;
    }


}
