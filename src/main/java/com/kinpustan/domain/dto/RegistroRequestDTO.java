package com.kinpustan.domain.dto;

import com.kinpustan.domain.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequestDTO(
    @NotBlank(message = "El username obligatorio")
    @Size(min = 3,max = 50,message =  "El username debe tener entre 3 y 50 caracteres")
    String username,

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password,

    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    String apellido,

    @NotBlank(message = "El rol es obligatorio")
    Rol rol
) {

}
