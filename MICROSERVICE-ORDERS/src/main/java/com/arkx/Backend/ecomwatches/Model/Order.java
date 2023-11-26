package com.arkx.Backend.ecomwatches.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
@Data
@NoArgsConstructor
@Document(collection = "orders")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Order {
    @Id
    private String id;
    private String customerId;

    private ArrayList<OrderItems> orderItems;
    private Integer orderDate;
    private Double cartTotalPrice;
    private String status;

    public Order(String id, String customerId, ArrayList<OrderItems> orderItems, Integer orderDate, Double cartTotalPrice, String status) {
        this.id = id;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.cartTotalPrice = cartTotalPrice;
        this.status = status;
    }

}
