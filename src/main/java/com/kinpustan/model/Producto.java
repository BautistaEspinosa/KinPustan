package com.kinpustan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Schema(description = "Entidad Producto")
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Identitificación unica del producto")
  private Long id;
  @Schema(description = "nuevo nombre del producto",
      example = "Coca-cola")
  private String nombre;
  @Schema(description = "Descripcion del producto",
      example = "coca de 600ml")
  private String descripcion;
  @Schema(description = "Especificación del precio"
      ,example = "4.0")
  private Double precio;
  @Schema(description = "Especificación de stock",
      example = "100")
  private Integer stock;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;
}
