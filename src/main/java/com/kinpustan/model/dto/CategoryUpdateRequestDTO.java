package com.kinpustan.model.dto;

import com.kinpustan.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Schema(description = "DTO para actualizar una categoría")
public class CategoryUpdateRequestDTO {

  private Long id;

  @Schema(description = "nuevo nombre de la categoría",
      example = "Bebidas")
  private String nombre;

  public CategoryUpdateRequestDTO(Categoria categoria) {
    this.id = categoria.getId();
    this.nombre = categoria.getNombre();
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
