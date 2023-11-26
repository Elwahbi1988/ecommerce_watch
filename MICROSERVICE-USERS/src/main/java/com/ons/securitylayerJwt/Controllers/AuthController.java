package com.ons.securitylayerJwt.Controllers;


import com.ons.securitylayerJwt.Entities.AppUser;
import com.ons.securitylayerJwt.Entities.DTO.UserDTO;
import com.ons.securitylayerJwt.Entities.DTO.UserResponseDTO;
import com.ons.securitylayerJwt.Services.AccountService;
import com.ons.securitylayerJwt.Entities.DTO.LoginDto;
import com.ons.securitylayerJwt.Entities.DTO.CustomerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {


    private final AccountService accountService;



    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody CustomerRequestDTO customerRequestDTO)
    {
       return  accountService.register(customerRequestDTO);
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginDto loginDto)
    { return  accountService.authenticate(loginDto);}

    @GetMapping(value = "/validate/{id}")
    public ResponseEntity<String> validateAccount(@PathVariable String id){
        String s = accountService.validateAccount(id);
        if(s.equals("account not exists")){
            return new ResponseEntity<>("account not exists", HttpStatus.NOT_FOUND);
        }

        else if(s.equals("account already validated")){
            return new ResponseEntity<>("account already validated", HttpStatus.BAD_REQUEST);
        }

        else{
            return new ResponseEntity<>("account successfully validated", HttpStatus.OK);
        }
    }

    @GetMapping("/get-user-details")
    public UserDTO getUserByEmail(@RequestParam String email) {
        UserDTO userByEmail = accountService.getUserByEmail(email);
        return userByEmail;

    }


}
