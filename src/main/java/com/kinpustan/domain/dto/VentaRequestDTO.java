package com.kinpustan.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record VentaRequestDTO (
   @NotNull(message = "El vendedor ID es obligatorio")
    Long vendedorId,

    LocalDateTime fechaVenta,
   @NotEmpty(message = "Debe incluir al menos un detalle de venta")
   @Valid
   List<DetalleVentaRequestDTO> detalleVentaDTOS
) {

}
