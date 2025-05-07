package com.kinpustan.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;

@Data
public class RegisterUserRequestDTO {

  @NotBlank(message = "El nombre es obligatorio")
  private String nombre;

  @NotBlank
  @Email(message = "Ingresa correo valido")
  private String correo;

  @NotBlank(message = "La contraseña es obligatoria")
  @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-_#]).{8,}$",
      message = "La contraseña debe incluir al menos una mayúscula, una minúscula,"
          + " un número y un carácter especial"
  )
  private String contrasenia;

  @NotNull(message = "Los roles no deben ser nulos")
  @Size(min = 1, message = "Debe incluir al menos un rol")
  private Set<@NotBlank(message = "Los roles no pueden estar vacíos") String> roles;
}
