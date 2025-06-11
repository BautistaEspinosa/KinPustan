package com.kinpustan.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "DTO para actualizar un producto")
@Data
public class ProductUpdateRequestDTO {

  @Schema(description = "nuevo nombre del producto",
      example = "Coca-cola")
  private String nombre;
  @Schema(description = "Descripcion del producto",
      example = "coca de 600ml")
  private String descripcion;
  @Schema(description = "Especificación del precio"
      , example = "4.0")
  private Double precio;
  @Schema(description = "Especificación de stock",
      example = "100")
  private Integer stock;
  @Schema(description = "Especificación de la categoria peretenciente",
  example = "bebidas")
  private Long categoriaId;

}
