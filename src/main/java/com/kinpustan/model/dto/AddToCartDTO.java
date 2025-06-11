package com.kinpustan.model.dto;

import lombok.Data;

@Data
public class AddToCartDTO {

  private Long cartSessionId;
  private Long productId;
  private String productName;
  private Double unitPrice;
  private Integer quantity;
}
