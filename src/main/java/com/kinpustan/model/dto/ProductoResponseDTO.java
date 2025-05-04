package com.kinpustan.model.dto;

import com.kinpustan.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
  private Long id;
  private String nombre;
  private String descripcion;
  private Double precio;
  private Integer stock;
  private CategoryUpdateRequestDTO categoria;

  public ProductoResponseDTO(Producto producto) {
    this.id = producto.getId();
    this.nombre = producto.getNombre();
    this.descripcion = producto.getDescripcion();
    this.precio = producto.getPrecio();
    this.stock = producto.getStock();
    if (producto.getCategoria() != null) {
      this.categoria = new CategoryUpdateRequestDTO(producto.getCategoria());
    } else {
      this.categoria = null;
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public CategoryUpdateRequestDTO getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoryUpdateRequestDTO categoria) {
    this.categoria = categoria;
  }
}
