package com.kinpustan.domain.dto;

import com.kinpustan.domain.enums.Rol;
import java.time.LocalDateTime;

public record UsuarioDTO(
    Long id,
    String username,
    String email,
    String nombre,
    String apellido,
    Rol rol,
    LocalDateTime fechaCreacion,
    Boolean activo

) {

}
