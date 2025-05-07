package com.kinpustan.model.dto;

import com.kinpustan.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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

}
