package com.kinpustan.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {

  @Email(message = "El correo no es valido.")
  private String correo;
  @Size(min=8)
  private String contrasenia;
}
