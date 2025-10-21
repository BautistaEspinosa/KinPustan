package com.kinpustan.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer cantidadSolicitada;

  @ManyToOne
  @JoinColumn(name = "producto_id")
  private Producto producto;

  @ManyToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;
}
