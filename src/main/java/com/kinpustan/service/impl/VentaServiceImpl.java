package com.kinpustan.service.impl;

import com.kinpustan.domain.dto.DetalleVentaRequestDTO;
import com.kinpustan.domain.dto.VentaRequestDTO;
import com.kinpustan.domain.dto.VentaResponseDTO;
import com.kinpustan.domain.entity.DetalleVenta;
import com.kinpustan.domain.entity.Producto;
import com.kinpustan.domain.entity.Venta;
import com.kinpustan.domain.mappers.VentaMapper;
import com.kinpustan.exception.ProductoNotFoundException;
import com.kinpustan.exception.StockInsuficienteException;
import com.kinpustan.exception.VentaNotFoundException;
import com.kinpustan.repository.ProductoRepository;
import com.kinpustan.repository.VentaRepository;
import com.kinpustan.service.VentaService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VentaServiceImpl implements VentaService {

  private final VentaRepository ventaRepository;
  private final ProductoRepository productoRepository;

  @Override
  @Transactional
  public VentaResponseDTO registraVenta(VentaRequestDTO ventaDTO) {

    log.info("Iniciando registro de venta para vendedor: {},", ventaDTO.vendedorId());

    try {

      validarVentaRequest(ventaDTO);

      Venta venta = new Venta();
      venta.setVendedorId(ventaDTO.vendedorId());
      venta.setFechaVenta(LocalDateTime.now());

      double total = 0.0;

      for (DetalleVentaRequestDTO detalleVentaDTO : ventaDTO.detalleVentaDTOS()) {
        Producto producto = productoRepository.findById(detalleVentaDTO.productoId())
            .orElseThrow(() -> new ProductoNotFoundException(detalleVentaDTO.productoId()));

        validarStock(producto, detalleVentaDTO.cantidad());

        producto.setStock(producto.getStock() - detalleVentaDTO.cantidad());
        productoRepository.save(producto);

        double subtotal = calcularSubtotal(producto.getPrecio(), detalleVentaDTO.cantidad());
        total += subtotal;

        DetalleVenta detalle = DetalleVenta.builder()
            .producto(producto)
            .cantidad(detalleVentaDTO.cantidad())
            .subtotal(subtotal)
            .venta(venta)
            .build();

        venta.getDetalles().add(detalle);
      }

      venta.setTotal(total);
      Venta ventaGuardada = ventaRepository.save(venta);

      log.info("Venta registrada exitosamente con ID: {} y total: ${}",
          ventaGuardada.getId(), ventaGuardada.getTotal());
      // Usamos el mapper aquí 👇
      return VentaMapper.toDTO(ventaGuardada);
    }catch (Exception e){
      log.error("Error al registrar venta para vendedor {}: {}",
          ventaDTO.vendedorId(), e.getMessage(), e);
      throw e;
    }
  }


  @Override
  public Page<VentaResponseDTO> listarVentas(Pageable pageable) {
    log.debug("Listando ventas con paginación: {}", pageable);
    return ventaRepository.findAll(pageable)
        .map(VentaMapper::toDTO);
  }

  @Override
  public List<Venta> listarVentas() {
    return ventaRepository.findAll();
  }

  @Override
  public Venta obtenerVenta(Long id) {
    log.debug("Obteniendo venta con ID: {}", id);
    return ventaRepository.findById(id)
        .orElseThrow(() -> new VentaNotFoundException(id));
  }

  @Override
  @Transactional
  public void eliminarVenta(Long id) {
    log.info("Eliminando venta con ID: {}", id);
    if (!ventaRepository.existsById(id)) {
      throw new VentaNotFoundException(id);
    }
    ventaRepository.deleteById(id);
    log.info("Venta eliminada exitosamente: {}", id);
  }

  // Métodos privados de validación
  private void validarVentaRequest(VentaRequestDTO ventaDTO) {
    if (ventaDTO.detalleVentaDTOS() == null || ventaDTO.detalleVentaDTOS().isEmpty()) {
      throw new IllegalArgumentException("La venta debe contener al menos un producto");
    }
  }

  private void validarStock(Producto producto, Integer cantidadSolicitada) {
    if (producto.getStock() < cantidadSolicitada) {
      throw new StockInsuficienteException(
          producto.getNombre(),
          producto.getStock(),
          cantidadSolicitada
      );
    }
  }

  private double calcularSubtotal(Double precio, Integer cantidad) {
    return precio * cantidad;
  }
}
