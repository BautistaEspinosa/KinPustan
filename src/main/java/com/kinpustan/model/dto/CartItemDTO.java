package com.kinpustan.model.dto;

import lombok.Data;

@Data
public class CartItemDTO {

  private Long id;
  private Long productId;
  private String productName;
  private Double unitPrice;
  private Integer quantity;
  private Double totalPrice;

  public CartItemDTO(Long id, Long productId, String productName, Double unitPrice,
      Integer quantity) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
    this.totalPrice = unitPrice * quantity;
  }
}
