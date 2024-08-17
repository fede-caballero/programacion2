package com.frcDev.NoInflation.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Se usa Optional porque findByEmail puede devolver null
    Optional<User> findByEmail(String email);
}
