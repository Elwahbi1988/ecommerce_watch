package com.ons.securitylayerJwt.Repositories;

import com.ons.securitylayerJwt.Entities.AppRole;
import com.ons.securitylayerJwt.Entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {

    Optional<AppRole> findByRoleName(String roleName);


}
