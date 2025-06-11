package com.kinpustan.service.impl;

import com.kinpustan.exception.CarritoNoEncontradoException;
import com.kinpustan.model.CartItem;
import com.kinpustan.model.CartSession;
import com.kinpustan.model.dto.AddToCartDTO;
import com.kinpustan.model.dto.CartItemDTO;
import com.kinpustan.model.dto.CartSumaryDTO;
import com.kinpustan.repository.CartItemRepository;
import com.kinpustan.repository.CartSessionRepository;
import com.kinpustan.service.CartService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private final CartSessionRepository sessionRepository;
  private final CartItemRepository cartItemRepository;

  public CartServiceImpl(CartSessionRepository sessionRepository,
      CartItemRepository cartItemRepository) {
    this.sessionRepository = sessionRepository;
    this.cartItemRepository = cartItemRepository;
  }

  @Override
  public Long createSession() {
    CartSession session = new CartSession();
    sessionRepository.save(session);
    return session.getId();
  }

  @Override
  public CartSumaryDTO addOrUpdateItem(AddToCartDTO dto) {
    CartSession session = sessionRepository.findById(dto.getCartSessionId())
        .orElseThrow(() -> new CarritoNoEncontradoException("No se encuentra"));
    List<CartItem> existing = cartItemRepository.findByCartSession(session).stream()
        .filter(i -> i.getProductId().equals(dto.getProductId()))
        .toList();
    CartItem item;
    if (!existing.isEmpty()) {
      item = existing.get(0);
      item.setQuantity(dto.getQuantity());
    } else {
      // Crea nuevo ítem
      item = new CartItem();
      item.setCartSession(session);
      item.setProductId(dto.getProductId());
      item.setProductName(dto.getProductName());
      item.setUnitPrice(dto.getUnitPrice());
      item.setQuantity(dto.getQuantity());
    }
    cartItemRepository.save(item);
    return buildSummary(session);
  }

  @Override
  public CartSumaryDTO removeItem(Long cartSessionId, Long itemId) {
    CartSession session = sessionRepository.findById(cartSessionId)
        .orElseThrow(() -> new CarritoNoEncontradoException("Carrito no encontrado"));
    cartItemRepository.deleteById(itemId);
    return buildSummary(session);
  }

  @Override
  public CartSumaryDTO getSummary(Long cartSessionId) {
    CartSession session = sessionRepository.findById(cartSessionId)
        .orElseThrow(() -> new CarritoNoEncontradoException("Carrito no encontrado"));
    return buildSummary(session);
  }

  @Override
  public Long checkout(Long cartSessionId, String paymentMethod) {
    // Lógica simplificada: en el futuro integrar pagos y crear entidad Order
    CartSession session = sessionRepository.findById(cartSessionId)
        .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
    // Aquí podrías validar stock, procesar pago, etc.
    // Después, limpiar la sesión o marcarla como completada
    sessionRepository.delete(session);
    return cartSessionId; // Temporal: devuelve mismo ID como confirmación
  }

  private CartSumaryDTO buildSummary(CartSession session) {
    List<CartItemDTO> items = cartItemRepository.findByCartSession(session).stream()
        .map(i -> new CartItemDTO(i.getId(), i.getProductId(),
            i.getProductName(), i.getUnitPrice(), i.getQuantity()))
        .collect(Collectors.toList());
    double tax = 0.0; // Por ahora no aplica impuestos
    return new CartSumaryDTO(session.getId(), items, tax);
  }
}
