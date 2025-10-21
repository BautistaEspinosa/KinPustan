package com.kinpustan.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DetalleVentaRequestDTO(
    @NotNull(message = "El producto ID es obligatorio")
    Long productoId,

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    Integer cantidad
) {

}
