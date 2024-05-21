package com.springboot.courses.config;

import com.springboot.courses.security.JwtAuthenticationEntryPoint;
import com.springboot.courses.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity

public class SecurityConfig {

    @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Cấu hình cors
        httpSecurity.cors(cors -> {
            cors.configurationSource(request -> {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.addAllowedOrigin("*");
                corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                corsConfig.addAllowedHeader("*");
                return corsConfig;
            });
        });
        httpSecurity.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/api/auth/handle/reset-password", "/api/auth/reset-password").hasAuthority("ROLE_CUSTOMER")
                                .requestMatchers("/api/auth/**").permitAll()

                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
