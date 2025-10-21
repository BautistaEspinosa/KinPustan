package com.kinpustan.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_venta")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DetalleVenta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer cantidad;
  private Double subtotal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "producto_id")
  private Producto producto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venta_id")
  private Venta venta;
}
