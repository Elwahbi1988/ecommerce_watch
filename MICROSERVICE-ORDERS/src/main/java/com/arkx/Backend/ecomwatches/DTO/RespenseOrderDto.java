package com.arkx.Backend.ecomwatches.DTO;

import com.arkx.Backend.ecomwatches.Model.OrderItems;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespenseOrderDto {
    private  String httpStatus;
    private  String id;
    private  String customerID;
    private  String customerFirstName;
    private  String customerLastName;
    private  List<OrderItems> orderItems;
    private  Long  itemsTotal;
    private  Integer orderDate;
    private  Double cartTotalPrice;
    private  String status;





}
