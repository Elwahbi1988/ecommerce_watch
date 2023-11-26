package com.ons.securitylayerJwt.Controllers;

import com.ons.securitylayerJwt.Entities.DTO.CustomerResponseDTO;
import com.ons.securitylayerJwt.Entities.DTO.UserResponseDTO;
import com.ons.securitylayerJwt.Services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/customers")
public class CustomerController {

    AccountService accountService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Page<CustomerResponseDTO>> readAllCustomers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "ASC") String sort,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "field", defaultValue = "firstName") String field){

        Page<CustomerResponseDTO> customers = accountService.listCustomers("CUSTOMER", page, size, field, sort);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(value = "/query")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Page<UserResponseDTO>> readCustomersByKw(@RequestParam(name = "query", defaultValue = "") String kw,
                                                                   @RequestParam(name = "page", defaultValue = "0")int page,
                                                                   @RequestParam(name = "size", defaultValue = "10")int size,
                                                                   @RequestParam(name = "field", defaultValue = "firstName") String field,
                                                                   @RequestParam(name = "sort", defaultValue = "ASC") String sort){

        Page<UserResponseDTO> result = accountService.findCustomerByKw(kw, page, size, field, sort);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<CustomerResponseDTO> readCustomer(@PathVariable String id){
        CustomerResponseDTO dto = accountService.findCustomerById(id);
        if(dto == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id){
        String s = accountService.unvalidateMyaccount(id);
        if(s.equals("user succesfully deleted")){
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/profile/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<CustomerResponseDTO> myProfile(@PathVariable String id){
        CustomerResponseDTO dto = accountService.findCustomerById(id);
        if(dto == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/unvalidate/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<String> unvalidateCustomer(@PathVariable String id){
        String s = accountService.unvalidateCustomerByID(id);
        if(s.equals("user succesfully unvalidated")){
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
        }
    }




}
