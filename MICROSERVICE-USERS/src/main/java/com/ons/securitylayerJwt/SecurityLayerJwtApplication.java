package com.ons.securitylayerJwt;

import com.ons.securitylayerJwt.Entities.AppUser;
import com.ons.securitylayerJwt.Services.AccountService;
import com.ons.securitylayerJwt.Entities.AppRole;
import com.ons.securitylayerJwt.Entities.RoleName;
import com.ons.securitylayerJwt.Repositories.AppRoleRepository;
import com.ons.securitylayerJwt.Repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;

@SpringBootApplication
public class SecurityLayerJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityLayerJwtApplication.class, args);
    }



    //@Bean
    CommandLineRunner run (AccountService accountService, AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder)
    {return  args ->
    {

        accountService.saveRole(new AppRole("CUSTOMER"));
        accountService.saveRole(new AppRole("MANAGER"));
        accountService.saveRole(new AppRole("ADMIN"));

        accountService.saveUser(new AppUser("manager@gmail.com", "manager fname",
                "manager lname",passwordEncoder.encode("managerPassword"), new ArrayList<>()));


        accountService.saveUser(new AppUser("admin@gmail.com",
                "admin fname","admin lname",passwordEncoder.encode("adminPassword"), new ArrayList<>()));

        accountService.saveUser(new AppUser("customer@gmail.com",
                "customer fname","customer lname",passwordEncoder.encode("customerPassword"), new ArrayList<>()));

        AppRole appRoleManager = appRoleRepository.findByRoleName("MANAGER").get();
        AppRole appRoleCustomer = appRoleRepository.findByRoleName("CUSTOMER").get();
        AppUser appUserManager = appUserRepository.findByEmail("manager@gmail.com").orElse(null);
        appUserManager.getAppRoles().add(appRoleManager);
        //appUserManager.getAppRoles().add(appRoleCustomer);
        accountService.saveUser(appUserManager);

        AppUser appUserAdmin = appUserRepository.findByEmail("admin@gmail.com").orElse(null);
        AppRole appRoleAdmin = appRoleRepository.findByRoleName("ADMIN").get();
        appUserAdmin.getAppRoles().add(appRoleAdmin);
        appUserAdmin.getAppRoles().add(appRoleManager);
        //appUserAdmin.getAppRoles().add(appRoleCustomer);
        accountService.saveUser(appUserAdmin);

        AppUser appUserCustomer = appUserRepository.findByEmail("customer@gmail.com").orElse(null);
        appUserCustomer.getAppRoles().add(appRoleCustomer);
        accountService.saveUser(appUserCustomer);


        System.out.println(appRoleRepository.findById("CUSTOMER").get().toString());










    };

    }

}

