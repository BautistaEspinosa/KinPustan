package com.kinpustan.service;

import com.kinpustan.domain.dto.VentaRequestDTO;
import com.kinpustan.domain.dto.VentaResponseDTO;
import com.kinpustan.domain.entity.Venta;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VentaService {

  VentaResponseDTO registraVenta(VentaRequestDTO ventaDTO);
  Venta obtenerVenta(Long id);
  List<Venta> listarVentas();
  Page<VentaResponseDTO> listarVentas(Pageable pageable);
  void eliminarVenta(Long id);
}
