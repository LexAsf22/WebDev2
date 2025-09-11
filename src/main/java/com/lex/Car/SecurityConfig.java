package com.lex.Car;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.lex.Car.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
    CustomUserDetailsService customUserDetailsService;
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.customUserDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/register","/login").permitAll() // public
                        .anyRequest().authenticated()             // secure everything else
                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/", true) // redirect after login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
