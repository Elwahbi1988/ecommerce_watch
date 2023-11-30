package com.arkx.Backend.ecomwatches.Service;

import com.arkx.Backend.ecomwatches.Model.Order;
import com.arkx.Backend.ecomwatches.Repository.OrderRepository;
import com.arkx.Backend.ecomwatches.enumeration.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public Order createOrder(Order order) {


        //setting status
        order.setStatus(OrderStatus.OPENED.getValue());
        //setting date
        Date dt = new Date();
        order.setOrderDate((int) dt.getTime());
        //sending order to be saved
        return orderRepository.save(order);
    }
// methode  dans Update order Status
    public Order SaveOrder(Order order) {
        return orderRepository.save(order);
    }
    public Order getOrderById(String id) {
        Optional<Order> opod = orderRepository.findById(id);
        if (opod.isEmpty()) {
            return null;
        }
        return opod.get();
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

 public void deleteOrderById(String id){
     orderRepository.deleteById(id);
 }


        public List<Order> findByCustomerId (Long customerId){
            // Utilisez la méthode du repository pour récupérer les commandes par customerId
            List<Order> orders = orderRepository.findByCustomerId(customerId);
            return orders;
        }
    }










