package com.kinpustan.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

public record VentaResponseDTO(
    Long id,
    Long vendedorId,
    LocalDateTime fechaVenta,
    Double total,
    List<DetalleVentaResponseDTO> detalles

) {
}