package com.ons.securitylayerJwt.Entities.DTO;

import com.ons.securitylayerJwt.Entities.AppRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponseDTO implements Serializable {

    String id;
    String firstName ;
    String lastName ;
    String email;
    List<AppRole> appRoles;


}
