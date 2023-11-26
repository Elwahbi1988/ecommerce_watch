package com.example.microservice3.security;

import com.example.microservice3.dto.UserDTO;
import com.example.microservice3.gateways.UserGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserGatewayService userGatewayService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      //  User user = iUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
        UserDTO userDTO = userGatewayService.getUserDetailsFromMicroservice(email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDTO.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return  new User(userDTO.getEmail(),userDTO.getPassword(),authorities);

    }


}
