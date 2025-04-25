package com.kinpustan.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "DTO para actualizar un producto")
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

  public Long getCategoriaId() {
    return categoriaId;
  }

  public void setCategoriaId(Long categoriaId) {
    this.categoriaId = categoriaId;
  }
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double precio) {
    this.precio = precio;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }
}
