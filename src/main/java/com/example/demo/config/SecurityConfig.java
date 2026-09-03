package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.filter.JwtFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtfilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http

    ) throws Exception {

        return http

                .cors(cors -> {})

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth


                        .requestMatchers(

                                "/auth/register",

                                "/auth/login",

                                "/ws/**",

                                "/messages/**",

                                "/show",

                                "/shows",

                                "/all-orders",

                                "/search/**",

                                "/upload",

                                "/uploads/**",

                                "/auth/doctor-users/**",

                                "/medicine/**",

                                "/create-order/**",

                                "/Singlemedicine/**"

                        ).permitAll()

                        .requestMatchers(

                                "/uploads",

                                "/doctor/**",

                                "/medicine-upload",

                                "/my-doctors/**",

                                "/my-medicines/**",

                                "/delete/**",

                                "/deleted/**",

                                "/update-doctor/**",

                                "/update-medicine/**"

                        )

                        .hasAnyAuthority(

                                "DOCTOR",

                                "ROLE_DOCTOR"

                        )

                        .requestMatchers(

                                "/user/**",

                                "/cart/**",

                                "/my-orders/**"

                        )

                        .hasAnyAuthority(

                                "USER",

                                "ROLE_USER"

                        )

                        .requestMatchers(

                                "/auth/premium-user",

                                "/auth/profile/**",

                                "/auth/avatar"

                        ).authenticated()


                        .requestMatchers(

                                "/save-order/**"

                        ).authenticated()


                        .anyRequest()

                        .authenticated()
                )

                .sessionManagement(session ->

                        session.sessionCreationPolicy(

                                SessionCreationPolicy.STATELESS
                        )
                )

                .addFilterBefore(

                        jwtfilter,

                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    public AuthenticationManager authenticationmanager(

            AuthenticationConfiguration config

    ) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(

                    CorsRegistry registry

            ) {

                registry.addMapping("/**")

                        .allowedOrigins(
    "http://localhost:5173",
    "https://medicare-project-frontened.vercel.app"
)

                        .allowedMethods("*")

                        .allowedHeaders("*");
            }


            @Override
            public void addResourceHandlers(

                    ResourceHandlerRegistry registry

            ) {


                registry

                        .addResourceHandler(

                                "/uploads/**"
                        )

                        .addResourceLocations(

                                "file:///C:/uploads/"
                        );


                registry

                        .addResourceHandler(

                                "/medicine/**"
                        )

                        .addResourceLocations(

                                "file:///C:/upload/medicine/"
                        );
            }
        };
    }
}
