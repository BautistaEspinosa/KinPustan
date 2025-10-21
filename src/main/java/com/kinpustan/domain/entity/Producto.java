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
@Table(name="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String descripcion;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="categoria_id",nullable = false)
  private Categoria categoria;
  private Double precio;
  private Integer stock;
}
