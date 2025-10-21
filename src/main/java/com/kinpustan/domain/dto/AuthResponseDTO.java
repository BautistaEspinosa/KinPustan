package com.kinpustan.domain.dto;

import com.kinpustan.domain.enums.Rol;

public record AuthResponseDTO(
    String token,
    String tokenType,
    Long usuarioId,
    String username,
    String email,
    String nombre,
    String apellido,
    Rol rol
) {
  public AuthResponseDTO(String token, Long usuarioId, String username, String email,
      String nombre,String apellido, Rol rol){
    this(token, "Bearer", usuarioId, username, email, nombre, apellido,rol);
  }
}
