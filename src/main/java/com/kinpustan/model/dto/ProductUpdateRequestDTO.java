package com.kinpustan.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequestDTO {
  private String nombre;
  private String descripcion;
  private Double precio;
  private Integer stock;
}
