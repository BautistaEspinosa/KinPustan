package com.kinpustan.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

@Value("${jwt.secret}")
  private String secret;
@Value("${jwt.expiration}")
  private long expirationMs;

private Key getSigningkey(){
  return Keys.hmacShaKeyFor(secret.getBytes());
}
public String generarToken(String correo){
  return Jwts.builder()
      .setSubject(correo)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis()+expirationMs))
      .signWith(getSigningkey(), SignatureAlgorithm.HS256)
      .compact();
}
public String extraerCorreo(String token){
  return Jwts.parserBuilder()
      .setSigningKey(getSigningkey())
      .build()
      .parseClaimsJwt(token)
      .getBody()
      .getSubject();
}

public boolean validarToken(String token){
  try{
    Jwts.parserBuilder()
        .setSigningKey(getSigningkey())
        .build()
        .parseClaimsJwt(token);
    return true;
  }catch (JwtException e){
    return false;
  }
}
}
