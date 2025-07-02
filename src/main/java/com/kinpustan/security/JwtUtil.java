package com.kinpustan.security;

import com.kinpustan.model.Rol;
import com.kinpustan.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expirationMs;

  private Key getSigningkey() {
    logger.debug("Generando clave de firma JWT");
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generarToken(Usuario user) {
    logger.info("Generando token para el usuario {} con correo {}", user.getNombre(),user.getCorreo());

    var roles = user.getRoles().stream().map(Rol::getNombre).toList();

    try {
      String token = Jwts.builder()
          .setSubject(user.getCorreo())
          .claim("roles", roles)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
          .signWith(getSigningkey(), SignatureAlgorithm.HS256)
          .compact();
      logger.debug("Token generado exitosamente");
      return token;
    } catch (Exception e) {
      logger.error("Error al generar el token: {}", e.getMessage(), e);
      throw e;
    }
  }

  public String extraerCorreo(String token) {
    logger.info("Extrayendo correo del token");
    try {
      String correo = Jwts.parserBuilder()
          .setSigningKey(getSigningkey())
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
      logger.debug("Correo extraído del token: {}", correo);
      return correo;
    } catch (JwtException e) {
      logger.warn("Error al extraer el correo del token: {}", e.getMessage());
      throw e;
    }
  }

  public boolean validarToken(String token) {
    logger.info("Validando token");
    try {
      Jwts.parserBuilder()
          .setSigningKey(getSigningkey())
          .build()
          .parseClaimsJws(token);
      logger.debug("Token válido");
      return true;
    } catch (JwtException e) {
      logger.warn("Token inválido: {}", e.getMessage());
      return false;
    }
  }
  public Claims extraerClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningkey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
