package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.demo.Repositorys.AuthRepository;
import com.example.demo.model.AuthLogin;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepository repo;
    
    

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException{

        AuthLogin login = repo.findByEmailId(emailId)
        		 .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (login == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(login.getEmailId()) // email ko username treat kar rahe
                .password(login.getPassword())
                .roles(login.getRole().replace("ROLE_", "")) 
                .build();
    }
}