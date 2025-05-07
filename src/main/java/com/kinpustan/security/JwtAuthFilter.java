package com.kinpustan.security;

import com.kinpustan.model.Usuario;
import com.kinpustan.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
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
  private final UsuarioRepository usuarioRepository;

  public JwtAuthFilter(JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
    this.jwtUtil = jwtUtil;
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      logger.debug("Token extraído del header: {}", token);

      if (jwtUtil.validarToken(token)) {
        logger.info("Token válido. Procesando autenticación.");

        String correo = jwtUtil.extraerCorreo(token);
        logger.debug("Correo extraído del token: {}", correo);

        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario != null) {
          var authorities = usuario.getRoles().stream()
              .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
              .collect(Collectors.toList());

          UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(correo, null, authorities);

          SecurityContextHolder.getContext().setAuthentication(auth);
          logger.info("Usuario autenticado: {} con roles: {}", correo,
              authorities.stream().map(Object::toString).collect(Collectors.joining(", ")));
        } else {
          logger.warn("No se encontró el usuario con correo: {}", correo);
        }
      } else {
        logger.warn("Token inválido recibido.");
      }
    } else {
      logger.debug("No se encontró el header Authorization o no comienza con 'Bearer '");
    }

    filterChain.doFilter(request, response);
  }
}
