package com.arkx.Backend.ecomwatches.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderItems")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderItems {
    private String itemID;
    private String itemName;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;


}
