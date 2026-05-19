package com.roya.the_club_application_backend.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDao extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByPhoneNumber(String phoneNumber);

    Optional<AppUser> findByEmail(String email);

}
