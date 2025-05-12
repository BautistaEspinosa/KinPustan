package com.kinpustan.config;

import com.kinpustan.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/auth/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/h2-console/**"
            ).permitAll()

            // ADMIN puede crear, actualizar y borrar productos y categorías
            .requestMatchers(HttpMethod.POST, "/api/products", "/api/catalogs").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/products/**", "/api/catalogs/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/products/**", "/api/catalogs/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST,"/api/ventas/**").hasRole("ADMIN")
            // USER puede ver productos y categorías
            .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/catalogs/**").hasAnyRole("USER", "ADMIN")

            // Cualquier otra petición necesita autenticación
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Para H2
    return http.build();
  }

}
