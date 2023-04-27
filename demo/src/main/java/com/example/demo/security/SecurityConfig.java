package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("123"))
                        .authorities("read", "write", "ROLE_ADMIN")
                        .build(),
                User.withUsername("estudiante")
                        .password(passwordEncoder().encode("123"))
                        .authorities("read", "ROLE_ESTUDIANTE")
                        .build(),
                User.withUsername("vigilante")
                        .password(passwordEncoder().encode("123"))
                        .authorities("read", "ROLE_VIGILANTE")
                        .build(),
                User.withUsername("responsable de aula")
                        .password(passwordEncoder().encode("123"))
                        .authorities("read", "ROLE_RESPONSABLEAULA")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .httpBasic()
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/notificaciones").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/notificaciones").hasAnyRole("ESTUDIANTE","ADMIN","VIGILANTE","RESPONSABLEAULA")
                .requestMatchers(HttpMethod.GET, "/notificaciones/{id}").hasAnyRole("ESTUDIANTE","ADMIN","VIGILANTE","RESPONSABLEAULA")
                .requestMatchers(HttpMethod.PUT, "/notificaciones/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/notificaciones/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/notificaciones/pendientes/abortar").hasRole("ADMIN")
                .and().csrf().disable().build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
}
