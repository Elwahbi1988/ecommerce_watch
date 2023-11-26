package com.ons.securitylayerJwt.Services;


import com.ons.securitylayerJwt.Entities.DTO.*;
import com.ons.securitylayerJwt.Entities.AppRole;
import com.ons.securitylayerJwt.Entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


public interface AccountService {
   //ResponseEntity<?> register (RegisterDto registerDto);
 //  ResponseEntity<BearerToken> authenticate(LoginDto loginDto);

   String authenticate(LoginDto loginDto);
   ResponseEntity<?> register (CustomerRequestDTO customerRequestDTO);
   AppRole saveRole(AppRole appRole);
   AppUser saveUser(AppUser appUser) ;
   ResponseEntity<?> addManager(UserRequestDTO userRequestDto);
   Page<UserResponseDTO> listUsersByRole(String role, int page, int pageSize, String field, String sort);
   Page<CustomerResponseDTO> listCustomers(String role, int page, int pageSize, String field, String sort);
   UserResponseDTO findUserById(String id);
   CustomerResponseDTO findCustomerById(String id);
   Page<UserResponseDTO> findUserByKw(String kw, int page, int pageSize, String field, String sort);
   Page<UserResponseDTO> findCustomerByKw(String kw, int page, int pageSize, String field, String sort);
   String unvalidateCustomerByID(String id);
   String deleteById(String id);
   String addRoleToUser(String id, String role);
   String deleteRoletoUser(String id, String role);
   String validateAccount(String id);
   public String unvalidateMyaccount(String id);

   public UserDTO getUserByEmail(String email);
}
