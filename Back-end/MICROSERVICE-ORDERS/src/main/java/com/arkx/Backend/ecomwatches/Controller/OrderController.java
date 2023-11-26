package com.arkx.Backend.ecomwatches.Controller;

import com.arkx.Backend.ecomwatches.DTO.Reponse;
import com.arkx.Backend.ecomwatches.DTO.RespenseOrderDto;
import com.arkx.Backend.ecomwatches.Model.Customer;
import com.arkx.Backend.ecomwatches.Model.Order;
import com.arkx.Backend.ecomwatches.Service.OrderService;
import io.jsonwebtoken.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1")
public class OrderController {
    @Autowired
    private OrderService orderService;
    private String secret = "2b44b0b00fd822d8ce753e54dac3dc4e06c2725f7db930f3b9924468b53194dbccdbe23d7baa5ef5fbc414ca4b2e64700bad60c5a7c45eaba56880985582fba4";

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Order order,
                            @RequestHeader("Authorization") String token) {
        List<String> roles = getListRolesFromToken(token);
        String id = getIdFromToken(token);

        if (roles.contains("CUSTOMER")){
            order.setCustomerId(id);
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Reponse("403", "you must be a customer"));
        }


    }



    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id,
                                          @RequestHeader("Authorization") String token) {

        List<String> roles = getListRolesFromToken(token);

        // Check if the user has admin role
        if (roles.contains("ADMIN") || roles.contains("MANAGER")) {
            Order o = orderService.getOrderById(id);
            if (o == null) {
                Reponse rep = new Reponse("404", "order not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(rep);
            }
            ModelMapper modelMapper = new ModelMapper();
            RespenseOrderDto reponse = modelMapper.map(o, RespenseOrderDto.class);


            //reponse.setCustomerFirstName(c.getFirstName());
            //reponse.setCustomerLastName(c.getLastName());


            return ResponseEntity.status(HttpStatus.OK)
                    .body(reponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String token ){

        List<String> roles = getListRolesFromToken(token);
        if (roles.contains("ADMIN") || roles.contains("MANAGER")){
            Pageable pageable = PageRequest.of(page, size);
            Page<Order> orders = orderService.getAllOrders(pageable);
            List<RespenseOrderDto> listeReponses = new ArrayList<>();
            for (Order o : orders) {


                RespenseOrderDto reponse = new RespenseOrderDto();
                reponse.setId(o.getId());
                reponse.setCustomerID(o.getCustomerId());
                reponse.setItemsTotal(o.getOrderItems().stream().count());
                reponse.setHttpStatus("200");
                reponse.setOrderDate(o.getOrderDate());
                reponse.setStatus(o.getStatus());
                reponse.setCartTotalPrice(o.getCartTotalPrice());
              //  reponse.setCustomerFirstName(c.getFirstName());
              //  reponse.setCustomerLastName(c.getLastName());
              //  List<Customer> listeClient = getListCustomer();

                listeReponses.add(reponse);
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(listeReponses);

        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Reponse("403", "you don't have enough privilege"));

    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestBody Map<String, String> reqBody,
                                               @RequestHeader("Authorization") String token){

        List<String> roles = getListRolesFromToken(token);
        // Check if the user has admin or manager role
        if (roles.contains("ADMIN") || roles.contains("MANAGER")){
            Order order = orderService.getOrderById(id);
            if (order == null ){
                return new ResponseEntity<>(new Reponse("404","invalid order id"),HttpStatus.OK);
            }else{
                order.setStatus(reqBody.get("status"));
                orderService.SaveOrder(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Reponse("403", "you don't have enough privilege"));

    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id,
                                         @RequestHeader("Authorization") String token) {

        List<String> roles = getListRolesFromToken(token);
        // Check if the user has admin or manager role
        if (roles.contains("ADMIN") || roles.contains("MANAGER")) {
            orderService.deleteOrderById(id);
            //return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
            return new ResponseEntity<>(new Reponse("200", "Order deleted successfully"), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Reponse("403", "you don't have enough privilege"));
        }
    }



    private String getIdFromToken(String token){
        try {
            // Validate the token
            Claims claims = Jwts.parser()
                    //.setSigningKey("MAJDA")
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            // Extract roles from claims
            String id = claims.get("id", String.class);

            return id;

        } catch (ExpiredJwtException eje) {
            // Handle token expiration
            System.out.println("Token has expired: " + eje.getMessage());
        } catch (MalformedJwtException mje) {
            // Handle malformed token
            System.out.println("Malformed token: " + mje.getMessage());
        } catch (SignatureException se) {
            // Handle signature mismatch
            System.out.println("Token signature is invalid: " + se.getMessage());
        } catch (IncorrectClaimException ice) {
            // Handle incorrect claims
            System.out.println("Incorrect claim: " + ice.getMessage());
        } catch (PrematureJwtException pje) {
            // Handle premature token
            System.out.println("Token is not yet valid: " + pje.getMessage());
        } catch (UnsupportedJwtException uje) {
            // Handle unsupported token format or algorithm
            System.out.println("Unsupported token: " + uje.getMessage());
        } catch (SecurityException se) {
            // Handle general security-related issues
            System.out.println("Security exception: " + se.getMessage());
        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            System.out.println("Unexpected error: " + ex.getMessage());
        }

        return null;

    }

    private List<String> getListRolesFromToken(String token){
        try {
            // Validate the token
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            // Extract roles from claims
            List<String> roles = claims.get("role", List.class);

            return roles;

        } catch (ExpiredJwtException eje) {
            // Handle token expiration
            System.out.println("Token has expired: " + eje.getMessage());
        } catch (MalformedJwtException mje) {
            // Handle malformed token
            System.out.println("Malformed token: " + mje.getMessage());
        } catch (SignatureException se) {
            // Handle signature mismatch
            System.out.println("Token signature is invalid: " + se.getMessage());
        } catch (IncorrectClaimException ice) {
            // Handle incorrect claims
            System.out.println("Incorrect claim: " + ice.getMessage());
        } catch (PrematureJwtException pje) {
            // Handle premature token
            System.out.println("Token is not yet valid: " + pje.getMessage());
        } catch (UnsupportedJwtException uje) {
            // Handle unsupported token format or algorithm
            System.out.println("Unsupported token: " + uje.getMessage());
        } catch (SecurityException se) {
            // Handle general security-related issues
            System.out.println("Security exception: " + se.getMessage());
        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            System.out.println("Unexpected error: " + ex.getMessage());
        }

        return null;

    }
}









