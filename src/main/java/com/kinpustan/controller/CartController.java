package com.kinpustan.controller;

import com.kinpustan.model.dto.AddToCartDTO;
import com.kinpustan.model.dto.CartSumaryDTO;
import com.kinpustan.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cart")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }
  @PostMapping
  public ResponseEntity<Long> createCart() {
    Long cartId = cartService.createSession();
    return ResponseEntity.ok(cartId);
  }

  /**
   * Agrega o actualiza un ítem en el carrito.
   */
  @PostMapping("/{cartId}/item")
  public ResponseEntity<CartSumaryDTO> addItem(
      @PathVariable Long cartId,
      @Valid @RequestBody AddToCartDTO dto) {
    dto.setCartSessionId(cartId);
    CartSumaryDTO summary = cartService.addOrUpdateItem(dto);
    return ResponseEntity.ok(summary);
  }

  /**
   * Elimina un ítem del carrito.
   */
  @DeleteMapping("/{cartId}/item/{itemId}")
  public ResponseEntity<CartSumaryDTO> removeItem(
      @PathVariable Long cartId,
      @PathVariable Long itemId) {
    CartSumaryDTO summary = cartService.removeItem(cartId, itemId);
    return ResponseEntity.ok(summary);
  }

  /**
   * Obtiene el resumen actual del carrito.
   */
  @GetMapping("/{cartId}")
  public ResponseEntity<CartSumaryDTO> getSummary(@PathVariable Long cartId) {
    CartSumaryDTO summary = cartService.getSummary(cartId);
    return ResponseEntity.ok(summary);
  }

  /**
   * Cierra la venta (checkout) y devuelve el ID de orden.
   */
  @PostMapping("/{cartId}/checkout")
  public ResponseEntity<Long> checkout(
      @PathVariable Long cartId,
      @RequestParam String paymentMethod) {
    Long orderId = cartService.checkout(cartId, paymentMethod);
    return ResponseEntity.ok(orderId);
  }
}
