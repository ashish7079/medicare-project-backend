package com.example.demo.Repositorys;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AuthLogin;

public interface AuthRepository
        extends JpaRepository<AuthLogin, Long> {

    Optional<AuthLogin>
    findByEmailId(String emailId);

    boolean existsByEmailId(
            String emailId
    );

    List<AuthLogin>
    findByIdIn(
            List<Long> ids
    );
}