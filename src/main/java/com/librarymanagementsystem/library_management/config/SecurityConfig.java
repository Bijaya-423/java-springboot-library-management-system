package com.librarymanagementsystem.library_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public
                .requestMatchers("/api/auth/**",
                    "/swagger-ui/**", "/swagger-ui.html",
                    "/v3/api-docs/**", "/v3/api-docs").permitAll()

                // Member Controller
                .requestMatchers(org.springframework.http.HttpMethod.GET,
                    "/api/members").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers(org.springframework.http.HttpMethod.GET,
                    "/api/members/**").hasAnyRole("ADMIN", "LIBRARIAN", "MEMBER")
                .requestMatchers(org.springframework.http.HttpMethod.POST,
                    "/api/members").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.PUT,
                    "/api/members/**").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE,
                    "/api/members/**").hasRole("ADMIN")

                // Book Controller
                .requestMatchers(org.springframework.http.HttpMethod.GET,
                    "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN", "MEMBER")
                .requestMatchers(org.springframework.http.HttpMethod.POST,
                    "/api/books").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers(org.springframework.http.HttpMethod.PUT,
                    "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE,
                    "/api/books/**").hasRole("ADMIN")

                // Borrow Controller
                .requestMatchers(org.springframework.http.HttpMethod.POST,
                    "/api/borrow").hasRole("MEMBER")
                .requestMatchers(org.springframework.http.HttpMethod.PUT,
                    "/api/borrow/return/**").hasRole("MEMBER")
                .requestMatchers(org.springframework.http.HttpMethod.GET,
                    "/api/borrow/history").hasRole("MEMBER")
                .requestMatchers(org.springframework.http.HttpMethod.GET,
                    "/api/borrow/all").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers(org.springframework.http.HttpMethod.GET,
                    "/api/borrow/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers(org.springframework.http.HttpMethod.PUT,
                    "/api/borrow/approve/**").hasRole("LIBRARIAN")
                .requestMatchers(org.springframework.http.HttpMethod.PUT,
                    "/api/borrow/reject/**").hasRole("LIBRARIAN")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}