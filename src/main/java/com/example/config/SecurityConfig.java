package com.example.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            //CSRF config (if enabled, will need to configure cookies policies)
            .csrf(csrf -> csrf.disable())
            //List authorized requests, including methods and routes
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/users/exists").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()

                    //Other requests must be authenticated
                    .anyRequest().authenticated()
            )
            .sessionManagement(
                sm -> sm
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .formLogin(f -> f.disable())
            .httpBasic(b -> b.disable())
            .cors(cors -> {})
        ;

        //TODO: http.addFilterBefore(...)
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
            "http://localhost:3000", 
            "https://my-app-ruby-gamma.vercel.app"
        ));
        config.setAllowedMethods(List.of(
            "GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"
        ));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return source;
    }
}
