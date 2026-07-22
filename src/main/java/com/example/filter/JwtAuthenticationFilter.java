package com.example.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.service.JwtService;

import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);
        
        if(token != null) {
            try {
                String username = jwtService.validateAndGetUsername(token);

                var auth = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.emptyList()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            catch(JwtException e) {
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        if(request.getCookies() == null) return null;

        for(Cookie c: request.getCookies()) {
            if("token".equals(c.getName()))
                return c.getValue();
        }

        return null;
    }
}
