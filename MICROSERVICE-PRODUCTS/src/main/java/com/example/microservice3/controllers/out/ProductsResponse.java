package com.example.microservice3.controllers.out;

import com.example.microservice3.dto.CategoryDTO;
import com.example.microservice3.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
    //private int status;
    private List<ProductDTO> data;
}
