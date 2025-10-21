package com.kinpustan.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @NotBlank(message = "El username es obligatorio")
    String username,

    @NotBlank(message = "La contraeña es obligatoria")
    String password
) {

}
