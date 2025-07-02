package com.kinpustan.security;

import com.kinpustan.model.Usuario;
import com.kinpustan.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

  private final JwtUtil jwtUtil;

  public JwtAuthFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    logger.debug("Entrando al metodo doFilterInternal");

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {

      String token = authHeader.substring(7);
      logger.debug("Token extraído del header: {}", token);

      if (jwtUtil.validarToken(token)) {
        logger.info("Token válido. Procesando autenticación.");

        // Extraer claims directamente del token
        Claims claims = jwtUtil.extraerClaims(token);
        String correo = claims.getSubject();

        List<String> roles = claims.get("roles", List.class);
        logger.debug("Roles extraídos del token: {}", roles);

        var authorities = roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(correo, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(auth);
        logger.info("Usuario autenticado: {} con roles: {}", correo, authorities);
      } else {
        logger.warn("Token inválido recibido.");
      }
    }

    filterChain.doFilter(request, response);
  }



}
