package com.ons.securitylayerJwt.Entities.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Data
@Setter
@Getter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDTO implements Serializable {

    String firstName ;
    String lastName ;
    String email;
    String password ;


}
