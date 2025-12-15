package com.tb.javaecommerce.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-COSMO-API-KEY";
    private final String validApiKey;

    public ApiKeyFilter(String validApiKey) {
        this.validApiKey = validApiKey;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/admin/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String apiKey = request.getHeader(HEADER_NAME);

        if (apiKey == null || !apiKey.equals(validApiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or missing API Key");
            return;
        }

        Authentication auth = new AbstractAuthenticationToken(
                List.of(new SimpleGrantedAuthority("ROLE_API_CLIENT"))
        ) {
            @Override
            public Object getCredentials() {
                return apiKey;
            }

            @Override
            public Object getPrincipal() {
                return "api-key-client";
            }
        };

        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
