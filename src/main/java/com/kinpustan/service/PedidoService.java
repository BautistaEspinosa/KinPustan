package com.kinpustan.service;

import com.kinpustan.domain.dto.PedidoDTO;
import com.kinpustan.domain.entity.Pedido;
import java.util.List;

public interface PedidoService {
  PedidoDTO crearPedido(PedidoDTO dto); // Vendedor crea pedido
  PedidoDTO obtenerPedidoPorId(Long id);
  List<PedidoDTO> listarPedidos();
  Pedido aprobarPedido(Long id); // Admin aprueba
  Pedido rechazarPedido(Long id); // Admin rechaza
 }
