package com.kinpustan.domain.dto;

import java.math.BigDecimal;

public record DetallePedidoDTO (
  Long id,
  Integer cantidadSolicitada,
  Long productoId, // solo mandamos el ID del producto
  String productoNombre
){}
