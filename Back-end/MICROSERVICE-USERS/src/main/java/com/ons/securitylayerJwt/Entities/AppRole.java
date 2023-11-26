package com.ons.securitylayerJwt.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppRole implements Serializable  {

    @Id
    String roleName ;


    public String getRoleName() {
        return roleName;
    }
}
