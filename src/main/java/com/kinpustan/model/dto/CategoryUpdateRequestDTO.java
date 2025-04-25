package com.kinpustan.model.dto;

import com.kinpustan.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class CategoryUpdateRequestDTO {

  @NotBlank(message = "El nombre no puede estar vacío")
  private String nombre;
  private Long id;
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
