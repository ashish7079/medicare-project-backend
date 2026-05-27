package com.example.demo.filter;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.AuthService;

import com.example.demo.util.JwtUtils;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter
        extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtutil;

    @Autowired
    private AuthService service;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filter

    ) throws ServletException, IOException {


        String path =
                request.getRequestURI();


        if (path.startsWith("/ws")) {

            filter.doFilter(
                    request,
                    response
            );

            return;
        }


        String header =
                request.getHeader(
                        "Authorization"
                );

        String token = null;

        String emailId = null;


        if (

                header != null

                &&

                header.startsWith(
                        "Bearer "
                )
        ) {

            token =
                    header.substring(7);

            emailId =
                    jwtutil.extractemail(
                            token
                    );

            if (

                    emailId != null

                    &&

                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null
            ) {

                UserDetails userdetails =

                        service.loadUserByUsername(
                                emailId
                        );

              
                if (

                        jwtutil.validateToken(

                                token,

                                userdetails.getUsername()
                        )
                ) {


                    String role =

                            jwtutil.extractRole(
                                    token
                            );

                  

                    List<SimpleGrantedAuthority>
                            authorities =

                            List.of(

                                    new SimpleGrantedAuthority(
                                            role
                                    )
                            );


                    UsernamePasswordAuthenticationToken
                            authToken =

                            new UsernamePasswordAuthenticationToken(

                                    userdetails,

                                    null,

                                    authorities
                            );

                    authToken.setDetails(

                            new WebAuthenticationDetailsSource()

                                    .buildDetails(
                                            request
                                    )
                    );
                    SecurityContextHolder

                            .getContext()

                            .setAuthentication(
                                    authToken
                            );
                }
            }
        }

        filter.doFilter(
                request,
                response
        );
    }
}