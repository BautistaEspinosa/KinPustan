package com.kinpustan.domain.dto;

import java.time.LocalDate;
import java.util.List;


public record PedidoDTO(
    Long id,
    Long vendedorId,
    LocalDate fechaSolicitud,
    String estado,
    LocalDate fechaRespuesta,
    List<DetallePedidoDTO> detalles
){}