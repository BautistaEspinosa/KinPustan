package com.kinpustan.controller;

import com.kinpustan.domain.dto.PedidoDTO;
import com.kinpustan.domain.entity.Pedido;
import com.kinpustan.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

  private final PedidoService pedidoService;

  @PostMapping
  public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
    PedidoDTO nuevoPedido = pedidoService.crearPedido(pedidoDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
  }

  @GetMapping
  public ResponseEntity<List<PedidoDTO>> listarPedidos() {
    return ResponseEntity.ok(pedidoService.listarPedidos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
    return ResponseEntity.ok(pedidoService.obtenerPedidoPorId(id));
  }

  @PutMapping("/{id}/aprobar")
  public ResponseEntity<Pedido> aprobarPedido(@PathVariable Long id) {
    return ResponseEntity.ok(pedidoService.aprobarPedido(id));
  }

  @PutMapping("/{id}/rechazar")
  public ResponseEntity<Pedido> rechazarPedido(@PathVariable Long id) {
    return ResponseEntity.ok(pedidoService.rechazarPedido(id));
  }
}
