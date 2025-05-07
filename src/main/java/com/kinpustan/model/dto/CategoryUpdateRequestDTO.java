package com.kinpustan.model.dto;

import com.kinpustan.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Schema(description = "DTO para actualizar una categoría")
public class CategoryUpdateRequestDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "nuevo nombre de la categoría",
      example = "Bebidas")
  private String nombre;

  public CategoryUpdateRequestDTO(Categoria categoria) {
    this.id = categoria.getId();
    this.nombre = categoria.getNombre();
  }
}
