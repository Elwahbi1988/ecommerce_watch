package com.example.microservice3.gateways;

import com.example.microservice3.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGatewayService {
    @Autowired
    private UserGateway userGateway;

    public UserDTO getUserDetailsFromMicroservice(String email) {
        return userGateway.getUserDetailsFromMicroservice(email);
    }
}
