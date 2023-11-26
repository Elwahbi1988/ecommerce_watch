package com.ons.securitylayerJwt.Services;

import com.ons.securitylayerJwt.Entities.AppUser;
import com.ons.securitylayerJwt.Entities.DTO.*;
import com.ons.securitylayerJwt.Entities.AppRole;
import com.ons.securitylayerJwt.Repositories.AppRoleRepository;
import com.ons.securitylayerJwt.Repositories.AppUserRepository;
import com.ons.securitylayerJwt.Security.JwtUtilities;
import com.ons.securitylayerJwt.Utils.EmailSenderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AuthenticationManager authenticationManager ;
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtilities jwtUtilities ;
    private EmailSenderService emailSenderService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public AppRole saveRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public ResponseEntity<?> addManager(UserRequestDTO userRequestDto) {

        if(appUserRepository.existsByEmail(userRequestDto.getEmail())){
            return  new ResponseEntity<>("email is already taken !", HttpStatus.SEE_OTHER);
        }
        else{
            AppUser appUser = new AppUser();
            appUser = modelMapper.map(userRequestDto, AppUser.class);
            appUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            appUser.setActive(true);
            appUser.setValidAccount(true);
            AppUser save = appUserRepository.save(appUser);

            AppRole appRoleManager = appRoleRepository.findByRoleName("MANAGER").get();
            //AppRole appRoleCustomer = appRoleRepository.findByRoleName("CUSTOMER").get();
            //AppRole appRoleAdmin = appRoleRepository.findByRoleName("ADMIN").get();

            AppUser user = appUserRepository.findByEmail(userRequestDto.getEmail()).orElse(null);
            user.getAppRoles().add(appRoleManager);
            //user.getAppRoles().add(appRoleCustomer);
           // if(userRequestDto.getRole().equalsIgnoreCase("admin")){
            //    user.getAppRoles().add(appRoleAdmin);
           // }


            UserResponseDTO map = modelMapper.map(user, UserResponseDTO.class);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }


    }

    @Override
    public Page<UserResponseDTO> listUsersByRole(String role, int page, int pageSize, String field, String sort) {
        Sort.Direction direction = Sort.Direction.DESC;
        if("ASC".equalsIgnoreCase(sort)){
            direction = Sort.Direction.ASC;
        }
        if(pageSize > 10) pageSize = 10;
        if(pageSize < 1) pageSize = 1;
        if(page < 0) page = 0;
        AppRole appRole = new AppRole(role);
        return appUserRepository.findAllUsersByRole(appRole, PageRequest.of(page, pageSize, Sort.by(direction, field)))
                .map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Override
    public Page<CustomerResponseDTO> listCustomers(String role, int page, int pageSize, String field, String sort) {
        Sort.Direction direction = Sort.Direction.DESC;
        if("ASC".equalsIgnoreCase(sort)){
            direction = Sort.Direction.ASC;
        }
        if(pageSize > 10) pageSize = 10;
        if(pageSize < 1) pageSize = 1;
        if(page < 0) page = 0;
        AppRole appRole = new AppRole(role);
        return appUserRepository.findAllUsersByRole(appRole, PageRequest.of(page, pageSize, Sort.by(direction, field)))
                .map(user -> modelMapper.map(user, CustomerResponseDTO.class));
    }

    @Override
    public ResponseEntity<?> register(CustomerRequestDTO customerRequestDTO) {

        Optional<AppUser> byEmail = appUserRepository.findByEmail(customerRequestDTO.getEmail());



        if(byEmail.isPresent() && byEmail.get().getValidAccount() == true)
            {
                return  new ResponseEntity<>("email is already taken !", HttpStatus.SEE_OTHER);
            }
        else if (byEmail.isPresent() && byEmail.get().getValidAccount() == false) {
            emailSenderService.sendSimpleEmail(byEmail.get().getEmail(),
                    "Hello, to validate your account plz click " +
                            "http://localhost:8081/v1/validate/"+
                            byEmail.get().getId(),
                    "Hello "+ byEmail.get().getFirstName()+", please validate your account");



            return  new ResponseEntity<>("account already exist, please validate your account", HttpStatus.SEE_OTHER);
             }
        else {
            AppUser appUser = new AppUser();
            appUser = modelMapper.map(customerRequestDTO, AppUser.class);
            appUser.setPassword(passwordEncoder.encode(customerRequestDTO.getPassword()));
            appUser.setActive(true);
            appUser.setValidAccount(false);

            //AppRole appRole = appRoleRepository.findByRoleName("CUSTOMER").get();
            //appUser.setAppRoles(Collections.singletonList(appRole));
            //String token = jwtUtilities.generateToken(customerRequestDTO.getEmail(),save.getId(), Collections.singletonList(appRole.getRoleName()));
            //return new ResponseEntity<>(new BearerToken(token , "Bearer "),HttpStatus.OK);

            AppUser save = appUserRepository.save(appUser);
            addRoleToUser(save.getId(), "CUSTOMER");
            appUserRepository.save(save);

            emailSenderService.sendSimpleEmail(save.getEmail(),
                    "Hello, to validate your account plz click " +
                            "http://localhost:8081/v1/validate/"+
                            save.getId(),
                    "Hello "+ save.getFirstName()+", please validate your account");




            return new ResponseEntity<>("account created, please check your email to validate it", HttpStatus.OK);


          }



        }

    @Override
    public String authenticate(LoginDto loginDto) {
      Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser appUser = appUserRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        appUser.getAppRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(appUser.getUsername(),appUser.getId(), rolesNames);
        appUser.setLastLogin(new Date());
        return token;
    }

    @Override
    public UserResponseDTO findUserById(String id) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if (byId.isPresent()){
            return modelMapper.map(byId, UserResponseDTO.class);
        }
        else{
            return null;
        }
    }

    @Override
    public CustomerResponseDTO findCustomerById(String id) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if (byId.isPresent()){
            if(byId.get().isManager()==false && byId.get().isAdmin() == false){
                return modelMapper.map(byId, CustomerResponseDTO.class);
            }
            return null;

        }
        else{
            return null;
        }
    }

    @Override
    public Page<UserResponseDTO> findUserByKw(String kw, int page, int pageSize, String field, String sort) {
        Sort.Direction direction = Sort.Direction.DESC;
        if("ASC".equalsIgnoreCase(sort)){
            direction = Sort.Direction.ASC;
        }

        if(pageSize > 10) pageSize = 10;
        if(pageSize < 1) pageSize = 1;
        if(page < 0) page = 0;

        AppRole role = new AppRole("MANAGER");

        return appUserRepository.findAllUsersByRoleAndQuery(role, kw,  PageRequest.of(page, pageSize, direction, field)).map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    public Page<UserResponseDTO> findCustomerByKw(String kw, int page, int pageSize, String field, String sort) {
        Sort.Direction direction = Sort.Direction.DESC;
        if("ASC".equalsIgnoreCase(sort)){
            direction = Sort.Direction.ASC;
        }

        if(pageSize > 10) pageSize = 10;
        if(pageSize < 1) pageSize = 1;
        if(page < 0) page = 0;

        AppRole role = new AppRole("CUSTOMER");

        return appUserRepository.findAllUsersByRoleAndQuery(role,  kw,PageRequest.of(page, pageSize, direction, field))
                .map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Override
    public String unvalidateCustomerByID(String id) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if(byId.isPresent() && byId.get().getActive()==true){
            if (byId.get().isCustomer()){
                byId.get().setActive(false);
                //byId.get().setEmail("DELETED/"+byId.get().getEmail());
                appUserRepository.save(byId.get());
                return "user succesfully unvalidated";

            }
            else{
                return byId.get().getEmail()+" is administrator";
            }
        }
        else{
            return "user not found";
        }
    }

    public String unvalidateMyaccount(String id) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if(byId.isPresent() && byId.get().getActive()==true){
                byId.get().setActive(false);
                byId.get().setEmail("DELETED/"+byId.get().getEmail());
                appUserRepository.save(byId.get());
                return "user succesfully deleted";
        }
        else{
            return "user not found";
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<AppUser> byId = appUserRepository.findByEmail(email);
        if(byId.isPresent()){
            UserDTO map = modelMapper.map(byId.get(), UserDTO.class);
            map.setRoles(byId.get().getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()));
            return map;
        }
        else {
            return null;
        }
    }

    @Override
    public String deleteById(String id) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if(byId.isPresent()){
            appUserRepository.deleteById(id);
            return "user succesfully deleted";
        }
        else{
            return "user not found";
        }
    }
    @Override
    public String addRoleToUser(String id, String role) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if(byId.isPresent()){
            if(byId.get().getActive() == true) {
                Optional<AppRole> byRoleName = appRoleRepository.findByRoleName(role);
                if (byRoleName.isPresent()) {
                    if (byRoleName.get().getRoleName().equalsIgnoreCase("CUSTOMER")) {
                        if (byId.get().isCustomer() == false && byId.get().isManager() == false && byId.get().isAdmin() == false) {
                            byId.get().getAppRoles().add(byRoleName.get());
                            appUserRepository.save(byId.get());
                            return byId.get().getEmail() + " is now a " + role;
                        } else {
                            return byId.get().getEmail() + " is already a customer or administrator";
                        }
                    }
                    if (byRoleName.get().getRoleName().equalsIgnoreCase("MANAGER")) {
                        if (byId.get().isManager() == false) {
                            byId.get().getAppRoles().add(byRoleName.get());
                            Optional<AppRole> customerRole = appRoleRepository.findByRoleName("CUSTOMER");
                            byId.get().getAppRoles().remove(customerRole.get());
                            appUserRepository.save(byId.get());
                            return byId.get().getEmail() + " is now a " + role;
                        } else {
                            return byId.get().getEmail() + " is already a " + role;
                        }
                    }

                    if (byRoleName.get().getRoleName().equalsIgnoreCase("ADMIN")) {
                        if (byId.get().isAdmin() == false) {
                            byId.get().getAppRoles().add(byRoleName.get());

                            if (byId.get().isManager() == false) {
                                Optional<AppRole> managerRole = appRoleRepository.findByRoleName("MANAGER");
                                byId.get().getAppRoles().add(managerRole.get());
                            }

                            if (byId.get().isCustomer() == true) {
                                Optional<AppRole> customerRole = appRoleRepository.findByRoleName("CUSTOMER");
                                byId.get().getAppRoles().remove(customerRole.get());
                            }

                            appUserRepository.save(byId.get());
                            return byId.get().getEmail() + " is now a " + role;
                        } else {
                            return byId.get().getEmail() + " is already a " + role;
                        }
                    } else {
                        return null;
                    }
                } else {
                    return "role not found";
                }
            }
            else{
                return "user is not active";
            }

        }
        else{
            return "user not found";
        }
    }

    @Override

    public String deleteRoletoUser(String id, String role) {

        Optional<AppUser> byId = appUserRepository.findById(id);
        if(byId.isPresent()){
            Optional<AppRole> byRoleName = appRoleRepository.findByRoleName(role);
            if(byRoleName.isPresent()){
                if(byRoleName.get().getRoleName().equalsIgnoreCase("CUSTOMER")){
                    if (byId.get().isCustomer() == true) {
                        byId.get().getAppRoles().remove(byRoleName.get());
                        appUserRepository.save(byId.get());
                        return byId.get().getEmail() + " is no longer a " + role;
                    } else {
                        return byId.get().getEmail() + " is not a customer";
                    }
                }
                if(byRoleName.get().getRoleName().equalsIgnoreCase("MANAGER")){
                    if (byId.get().isManager() == true) {
                        byId.get().getAppRoles().remove(byRoleName.get());
                        if(byId.get().isAdmin() == true){
                            Optional<AppRole> customerRole = appRoleRepository.findByRoleName("ADMIN");
                            byId.get().getAppRoles().remove(customerRole.get());
                        }
                        if(byId.get().isCustomer() == false){
                            Optional<AppRole> customerRole = appRoleRepository.findByRoleName("CUSTOMER");
                            byId.get().getAppRoles().add(customerRole.get());
                        }
                        appUserRepository.save(byId.get());
                        return byId.get().getEmail() + " is no longer a " + role;
                    } else {
                        return byId.get().getEmail() + " is not a " + role;
                    }
                }

                if(byRoleName.get().getRoleName().equalsIgnoreCase("ADMIN")){
                    if (byId.get().isAdmin() == true) {
                        byId.get().getAppRoles().remove(byRoleName.get());

                        appUserRepository.save(byId.get());
                        return byId.get().getEmail() + " is no longer a " + role;
                    } else {
                        return byId.get().getEmail() + " is not a " + role;
                    }
                }
                else{
                    return null;
                }
            }
            else {
                return "role not found";
            }

        }
        else{
            return "user not found";
        }
    }

    @Override
    public String validateAccount(String id){
        Optional<AppUser> byId = appUserRepository.findById(id);

        if(!byId.isPresent()){
            return "account not exists";
        }

        else if(byId.isPresent() && byId.get().getValidAccount() == true){
            return "account already validated";
        }

        else {
            byId.get().setValidAccount(true);
            appUserRepository.save(byId.get());
            return "account successfully validated";
        }
    }


}

