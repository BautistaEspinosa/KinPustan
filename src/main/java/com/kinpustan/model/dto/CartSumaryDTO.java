package com.kinpustan.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class CartSumaryDTO {

  private Long cartSessionId;
  private List<CartItemDTO> items;
  private Double subtotal;
  private Double tax;
  private Double total;

  public CartSumaryDTO(Long cartSessionId, List<CartItemDTO> items, Double tax) {
    this.cartSessionId = cartSessionId;
    this.items = items;
    this.subtotal = items.stream().mapToDouble(CartItemDTO::getUnitPrice).sum();
    this.tax = tax;
    this.total = this.subtotal+(tax != null ? total : 0);
  }
}
