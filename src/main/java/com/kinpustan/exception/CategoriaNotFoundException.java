package com.kinpustan.exception;

public class CategoriaNotFoundException extends BusinessException {
  public CategoriaNotFoundException(Long id) {
    super(String.format("Categoría con ID %d no encontrada", id));
  }
}
