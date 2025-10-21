package com.kinpustan.domain.mappers;

import com.kinpustan.domain.dto.DetalleVentaResponseDTO;
import com.kinpustan.domain.dto.VentaResponseDTO;
import com.kinpustan.domain.entity.DetalleVenta;
import com.kinpustan.domain.entity.Venta;
import java.util.List;

public class VentaMapper {
  public static VentaResponseDTO toDTO(Venta venta) {
    List<DetalleVentaResponseDTO> detallesDTO = venta.getDetalles().stream()
        .map(VentaMapper::toDetalleDTO)
        .toList();

    return new VentaResponseDTO(
        venta.getId(),
        venta.getVendedorId(),
        venta.getFechaVenta(),
        venta.getTotal(),
        detallesDTO
    );
  }

  private static DetalleVentaResponseDTO toDetalleDTO(DetalleVenta detalle) {
    return new DetalleVentaResponseDTO(
        detalle.getProducto().getId(),
        detalle.getProducto().getNombre(),
        detalle.getCantidad(),
        detalle.getSubtotal()
    );
  }
}
