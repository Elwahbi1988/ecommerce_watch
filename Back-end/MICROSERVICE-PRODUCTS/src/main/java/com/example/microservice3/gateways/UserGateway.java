package com.example.microservice3.gateways;

import com.example.microservice3.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "user-Microservice",url = "http://localhost:8081/v1/")
public interface UserGateway {
    @GetMapping("/get-user-details")
    UserDTO getUserDetailsFromMicroservice(@RequestParam String email);
}
