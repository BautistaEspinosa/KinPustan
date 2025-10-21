package com.kinpustan.domain.dto;

import com.kinpustan.domain.entity.Categoria;
import com.kinpustan.domain.entity.Producto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public record ProductoDTO (
    Long id,

    @NotBlank(message = "El nombre del producto es obligatorio")
    String nombre,

    String descripcion,

    @NotNull(message = "La categoría es obligatoria")
    Long categoriaId,

    String categoriaNombre,

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    Double precio,

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    Integer stock
){
  public static ProductoDTO from(Producto producto) {
    return new ProductoDTO(
        producto.getId(),
        producto.getNombre(),
        producto.getDescripcion(),
        producto.getCategoria() != null ? producto.getCategoria().getId() : null,
        producto.getCategoria() != null ? producto.getCategoria().getNombre() : null,
        producto.getPrecio(),
        producto.getStock()
    );
  }
}
