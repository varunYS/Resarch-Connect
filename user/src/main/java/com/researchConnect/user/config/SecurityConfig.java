package com.researchConnect.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF since we are building a stateless REST API (using JWTs later)
                .csrf(csrf -> csrf.disable())

                // Define URL authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to hit our signup endpoint without logging in
                        .requestMatchers("/user/signup", "/user/login").permitAll()
                        // Secure all other endpoints by default
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}