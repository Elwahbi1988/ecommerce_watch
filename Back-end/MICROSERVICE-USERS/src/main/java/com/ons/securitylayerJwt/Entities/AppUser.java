package com.ons.securitylayerJwt.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUser implements Serializable , UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    @NotBlank @NotNull @Size(min = 3, max = 20)
    String firstName ;
    @NotBlank @NotNull @Size(min = 3, max = 20)
    String lastName ;
    @Column(unique=true) @NotBlank @NotNull
    String email;
    String password ;
    //@Column(unique=true) @NotBlank @NotNull @Size(min = 3, max = 20)
    //String username;
    @CreationTimestamp
    private Date creationDate;
    private Date lastLogin;
    @CreationTimestamp
    private Date lastUpdate;
    private Boolean validAccount;
    private Boolean active;

    //Un utilisateur peut avoir plusieurs roles
    @ManyToMany(fetch = FetchType.EAGER  , cascade = CascadeType.PERSIST)
    List<AppRole> appRoles = new ArrayList<>();


    public AppUser(String email, String firstName, String lastName, String password , List<AppRole> appRoles) {
      this.email= email ;
      //this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
      this.password=password ;
      this.appRoles = appRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.appRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Boolean isManager(){
        return this.getAppRoles().stream()
                .map(r -> r.getRoleName().toString())
                .collect(Collectors.toList())
                .contains("MANAGER");
    }

    public Boolean isAdmin(){
        return this.getAppRoles().stream()
                .map(r -> r.getRoleName().toString())
                .collect(Collectors.toList())
                .contains("ADMIN");
    }

    public Boolean isCustomer(){
        return this.getAppRoles().stream()
                .map(r -> r.getRoleName().toString())
                .collect(Collectors.toList())
                .contains("CUSTOMER");
    }
}
