package com.kinpustan.model.dto;

import com.kinpustan.model.DetalleVenta;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class VentaRequestDTO {

  @NotNull(message = "El nombre del cliente no puede ser nulo")
  private String cliente;

  @NotNull(message = "El detalle de la venta no puede ser nulo")
  private List<DetalleVentaDTO> detalleVentas;
}
