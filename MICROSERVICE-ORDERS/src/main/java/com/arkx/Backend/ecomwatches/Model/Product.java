package com.arkx.Backend.ecomwatches.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.w3c.dom.Text;
@Data
@NoArgsConstructor
@Document(collection = "products")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Product {
    private String id;
    private String sku;
    private String productImage;
    private String productName;
    private String subcategoryId;
    private Text shortDescription;
    private Text longDescription;
    private Double price;
    private Double discountPrice;
    private String status;



}
