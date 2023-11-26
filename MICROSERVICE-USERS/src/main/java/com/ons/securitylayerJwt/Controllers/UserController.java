package com.ons.securitylayerJwt.Controllers;


import com.ons.securitylayerJwt.Entities.DTO.UserRequestDTO;
import com.ons.securitylayerJwt.Entities.DTO.UserResponseDTO;
import com.ons.securitylayerJwt.Services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class UserController {


    AccountService accountService;



    @PostMapping("/managers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addManager(@RequestBody UserRequestDTO userRequestDTO){
        return accountService.addManager(userRequestDTO);
    }
    @GetMapping("/managers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> readAllManagers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "ASC") String sort,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "field", defaultValue = "firstName") String field){

        Page<UserResponseDTO> customers = accountService.listUsersByRole("MANAGER", page, size, field, sort);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> readAllAdmins(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "ASC") String sort,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "field", defaultValue = "firstName") String field){

        Page<UserResponseDTO> customers = accountService.listUsersByRole("ADMIN", page, size, field, sort);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @DeleteMapping(value = "/users/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        String s = accountService.deleteById(id);
        if(s.equals("user succesfully deleted")){
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/users/addrole/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addRoleToUser(@PathVariable String id,
                                                @RequestParam String role){

        String s = accountService.addRoleToUser(id, role);
        if(s.equals("role not found") || s.equals("user not found")){
            return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/users/deleterole/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteRoleToUser(@PathVariable String id,
                                                @RequestParam String role){

        String s = accountService.deleteRoletoUser(id, role);
        if(s.equals("role not found") || s.equals("user not found")){
            return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
    }

    //////////////////////////////////// manager options ////////////////////////



    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<UserResponseDTO> readUser(@PathVariable String id){
        UserResponseDTO dto = accountService.findUserById(id);
        if(dto == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/users/query")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Page<UserResponseDTO>> readUserByKw(@RequestParam(name = "query", defaultValue = "") String kw,
                                                              @RequestParam(name = "page", defaultValue = "0")int page,
                                                              @RequestParam(name = "size", defaultValue = "10")int size,
                                                              @RequestParam(name = "field", defaultValue = "firstName") String field,
                                                              @RequestParam(name = "sort", defaultValue = "ASC") String sort){

        Page<UserResponseDTO> result = accountService.findUserByKw(kw, page, size, field, sort);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }




}
