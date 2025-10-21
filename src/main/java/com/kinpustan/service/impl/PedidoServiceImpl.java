package com.kinpustan.service.impl;

import com.kinpustan.domain.dto.DetallePedidoDTO;
import com.kinpustan.domain.dto.PedidoDTO;
import com.kinpustan.domain.entity.DetallePedido;
import com.kinpustan.domain.entity.Pedido;
import com.kinpustan.domain.entity.Producto;
import com.kinpustan.repository.PedidoRepository;
import com.kinpustan.repository.ProductoRepository;
import com.kinpustan.service.PedidoService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

  private final PedidoRepository pedidoRepository;
  private final ProductoRepository productoRepository;

  @Override
  public PedidoDTO crearPedido(PedidoDTO dto) {
    Pedido pedido = new Pedido();
    pedido.setVendedorId(dto.vendedorId());
    pedido.setFechaSolicitud(LocalDate.now());
    pedido.setEstado("PENDIENTE");

    List<DetallePedido> detallePedidos = dto.detalles().stream().map(d -> {
      Producto producto = productoRepository.findById(d.productoId())
          .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
      return DetallePedido.builder()
          .cantidadSolicitada(d.cantidadSolicitada())
          .producto(producto)
          .pedido(pedido)
          .build();
    }).toList();
    pedido.setDetalles(detallePedidos);

    Pedido pedidoguardado = pedidoRepository.save(pedido);
    return ToDTO(pedidoguardado);
  }

  @Override
  public PedidoDTO obtenerPedidoPorId(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

    return ToDTO(pedido);
  }

  @Override
  public List<PedidoDTO> listarPedidos() {
    return pedidoRepository.findAll().stream()
        .map(this::ToDTO)
        .toList();
  }

  @Override
  public Pedido aprobarPedido(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    pedido.setEstado("APROBADO");
    pedido.setFechaRespuesta(LocalDate.now());

    // 💡 aquí actualizamos stock de cada producto
    for (DetallePedido d : pedido.getDetalles()) {
      Producto producto = d.getProducto();
      producto.setStock(producto.getStock() + d.getCantidadSolicitada());
      productoRepository.save(producto);
    }

    return pedidoRepository.save(pedido);
  }

  @Override
  public Pedido rechazarPedido(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    pedido.setEstado("RECHAZADO");
    pedido.setFechaRespuesta(LocalDate.now());
    return pedidoRepository.save(pedido);
  }

  // 🔹 Mapper Entidad → DTO
  private PedidoDTO ToDTO(Pedido pedido) {
    List<DetallePedidoDTO> detallesDTO = pedido.getDetalles().stream()
        .map(d -> new DetallePedidoDTO(
            d.getId(),
            d.getCantidadSolicitada(),
            d.getProducto().getId(),
            d.getProducto().getNombre()
        ))
        .toList();

    return new PedidoDTO(
        pedido.getId(),
        pedido.getVendedorId(),
        pedido.getFechaSolicitud(),
        pedido.getEstado(),
        pedido.getFechaRespuesta(),
        detallesDTO
    );
  }
}
