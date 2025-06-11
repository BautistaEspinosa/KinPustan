package com.kinpustan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_session_id",nullable = false)
  private CartSession cartSession;

  private Long productId;
  private String productName;
  private Double unitPrice;
  private Integer quantity;
}
