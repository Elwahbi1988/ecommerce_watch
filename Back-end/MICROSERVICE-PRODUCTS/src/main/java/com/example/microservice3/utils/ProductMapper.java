package com.example.microservice3.utils;

import com.example.microservice3.controllers.in.ProductRequest;
import com.example.microservice3.dto.ProductDTO;
import com.example.microservice3.dto.SubCategoryDTO;
import com.example.microservice3.entities.Category;
import com.example.microservice3.entities.Product;
import com.example.microservice3.entities.SubCategory;
import org.modelmapper.ModelMapper;

public class ProductMapper {
//    private static final ModelMapper modelMapper = new ModelMapper();
//    public static ProductDTO mapProductRequestToProductDTO(ProductRequest productRequest){
//        return modelMapper.map(productRequest,ProductDTO.class);
//    }
    public static ProductDTO mapProductRequestToProductDTO (ProductRequest productRequest){

        return ProductDTO.builder()
                .sku(productRequest.getSku())
                .productName(productRequest.getProductName())
                .productImage(productRequest.getProductImage())
                .subCategoryId(productRequest.getSubCategoryId())
                .discountPrice(productRequest.getDiscountPrice())
                .price(productRequest.getPrice())
                .longDescription(productRequest.getLongDescription())
                .shortDescription(productRequest.getShortDescription())
                .subCategoryId(productRequest.getSubCategoryId())
                .quantity(productRequest.getQuantity())
                .build();
    }

//
//    public static ProductDTO mapProductProductDTO(Product product) {
//        ProductDTO productDTO = ProductDTO.builder()
//                ._id(product.getId())
//                .productName(product.getProductName())
//                .active(product.getActive())
//                .build();
//
//        SubCategory subCategory = product.getSubCategory();
//        if (subCategory != null) {
//            productDTO.setSubCategoryId(subCategory.getId());
//            productDTO.setSubCategoryName(subCategory.getSubCategoryName());
//        }
//
//        return productDTO;
//    }

    public static ProductDTO mapProductToProductDTO(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                ._id(product.getId())
                .productName(product.getProductName())
                .sku(product.getSku())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .discountPrice(product.getDiscountPrice())
                .price(product.getPrice())
                .longDescription(product.getLongDescription())
                .shortDescription(product.getShortDescription())
                .quantity(product.getQuantity())
                .active(product.getActive())
                .build();

        SubCategory subCategory = product.getSubCategory();
        if (subCategory != null) {
            productDTO.setSubCategoryId(subCategory.getId());
            productDTO.setSubCategoryName(subCategory.getSubCategoryName());
//
//            Category category = subCategory.getCategory();
//            if (category != null) {
//                productDTO.setCategoryName(category.getCategoryName());
//            }
        }

        return productDTO;
    }


}
