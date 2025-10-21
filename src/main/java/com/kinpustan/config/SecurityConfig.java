package com.kinpustan.config;

import com.kinpustan.repository.UsuarioRepository;
import com.kinpustan.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  //private final JwtAuthenticationFilter jwtAuthFilter;
  private final UsuarioRepository usuarioRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      JwtAuthenticationFilter jwtAuthFilter) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/swagger-ui.html").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers("/swagger-resources/**").permitAll()
            .requestMatchers("/webjars/**").permitAll()
            .requestMatchers("/error").permitAll()

            // Endpoints públicos
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()

            // Endpoints solo para ADMIN
            .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/categorias/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/pedidos/*/aprobar").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/pedidos/*/rechazar").hasRole("ADMIN")

            // Endpoints para VENDEDOR y ADMIN
            .requestMatchers("/api/ventas/**").hasAnyRole("VENDEDOR", "ADMIN")
            .requestMatchers("/api/pedidos/**").hasAnyRole("VENDEDOR", "ADMIN")

            // Cualquier otra request requiere autenticación
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
