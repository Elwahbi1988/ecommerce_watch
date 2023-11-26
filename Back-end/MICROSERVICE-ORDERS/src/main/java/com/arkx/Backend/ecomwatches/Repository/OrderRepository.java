package com.arkx.Backend.ecomwatches.Repository;


import com.arkx.Backend.ecomwatches.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
@RestResource(path="/byCustomerId")
    List<Order> findByCustomerId(@Param("customerId")Long customerId);

}