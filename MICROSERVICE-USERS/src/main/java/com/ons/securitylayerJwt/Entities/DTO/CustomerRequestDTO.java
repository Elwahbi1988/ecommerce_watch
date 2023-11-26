package com.ons.securitylayerJwt.Entities.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequestDTO implements Serializable {

    String firstName ;
    String lastName ;
    String email;
    //String username;
    String password ;
}
