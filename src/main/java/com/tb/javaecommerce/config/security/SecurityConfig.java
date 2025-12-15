package com.tb.javaecommerce.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("!no-auth")
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${security.api-key}")
    private String apiKey;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        ApiKeyFilter apiKeyFilter = new ApiKeyFilter(apiKey);

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/api-docs/**"
                ).permitAll()

                .requestMatchers("/api/admin/**").authenticated()

                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                .anyRequest().authenticated()
            )

            .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(Customizer.withDefaults())
            )

            .oauth2Login(oauth -> oauth
                    .defaultSuccessUrl("/swagger-ui/index.html", true)
            )

            .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
