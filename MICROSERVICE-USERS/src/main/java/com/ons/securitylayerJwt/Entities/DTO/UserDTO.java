package com.ons.securitylayerJwt.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String id ;
    private String firstName ;
    private String lastName ;
    private String email;
    private String password ;
    private List<String> roles = new ArrayList<>();
}
