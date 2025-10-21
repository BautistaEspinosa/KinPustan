package com.kinpustan.domain.dto;

import lombok.Data;

public record DetalleVentaResponseDTO(
    Long productoId,
    String productoNombre,
    Integer cantidad,
    Double subtotal
) {
}