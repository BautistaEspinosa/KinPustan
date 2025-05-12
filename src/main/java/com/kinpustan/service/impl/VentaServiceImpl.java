package com.kinpustan.service.impl;

import com.kinpustan.exception.ProductoNoEncontradoException;
import com.kinpustan.model.DetalleVenta;
import com.kinpustan.model.Producto;
import com.kinpustan.model.Venta;
import com.kinpustan.model.dto.DetalleVentaDTO;
import com.kinpustan.model.dto.VentaRequestDTO;
import com.kinpustan.repository.DetalleVentaRepository;
import com.kinpustan.repository.ProductRepository;
import com.kinpustan.repository.VentaRepository;
import com.kinpustan.service.VentaService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl implements VentaService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private VentaRepository ventaRepository;
  @Autowired
  private DetalleVentaRepository detalleVentaRepository;

  @Transactional
  @Override
  public String registraVenta(VentaRequestDTO ventaRequestDTO) {
    Venta venta = new Venta();
    venta.setCliente(ventaRequestDTO.getCliente());
    venta.setFecha(LocalDateTime.now());

    List<DetalleVenta> detalles = ventaRequestDTO.getDetalleVentas().stream()
        .map(dto -> {
          Producto producto = productRepository.findById(dto.getProductId())
              .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));

          if(producto.getStock() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
          }

          producto.setStock(producto.getStock() - dto.getCantidad());
          productRepository.save(producto);

          DetalleVenta detalle = new DetalleVenta();
          detalle.setProducto(producto);
          detalle.setCantidad(dto.getCantidad());
          detalle.setPrecioUnitario(producto.getPrecio());
          detalle.setVentas(venta);  // Establece la relación bidireccional

          return detalle;
        }).toList();

    double total = detalles.stream()
        .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
        .sum();

    venta.setTotal(total);
    venta.setDetalleVentas(detalles);
    ventaRepository.save(venta);  // CascadeType.ALL guardará los detalles automáticamente

    return "Venta registrada. Total: $" + total;
  }
}
