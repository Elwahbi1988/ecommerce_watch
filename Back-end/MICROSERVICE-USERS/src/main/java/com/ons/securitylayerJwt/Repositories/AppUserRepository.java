package com.ons.securitylayerJwt.Repositories;

import com.ons.securitylayerJwt.Entities.AppRole;
import com.ons.securitylayerJwt.Entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,String> {

    Boolean existsByEmail(String email);
    //Boolean existsByUsername(String username);
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findById(String id);
    @Query("SELECT u FROM AppUser u WHERE u.active = TRUE AND :role MEMBER OF u.appRoles")
    Page<AppUser> findAllUsersByRole(@Param("role") AppRole appRole, Pageable pageable);


    @Query("SELECT u FROM AppUser u WHERE (u.active = TRUE) AND (:role MEMBER OF u.appRoles) AND (u.firstName LIKE %:mc% OR u.lastName LIKE %:mc% OR u.email LIKE %:mc%) ")
    Page<AppUser> findAllUsersByRoleAndQuery(@Param("role") AppRole appRole, @Param("mc") String mc, Pageable pageable);








    //@Query("SELECT u FROM AppUser u WHERE ((u.active = TRUE) AND (:role MEMBER OF u.appRoles)) AND (:mc)")
    //Page<AppUser> findUserByKw(@Param("mc") String mc ,@Param("role") AppRole appRole, Pageable pageable);



    //WHERE (u.firstName LIKE :mc OR u.lastName LIKE :mc OR u.email LIKE :mc)







}


