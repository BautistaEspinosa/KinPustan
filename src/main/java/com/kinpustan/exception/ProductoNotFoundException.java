package com.kinpustan.exception;
public class ProductoNotFoundException extends BusinessException {
  public ProductoNotFoundException(Long id) {
    super(String.format("Producto con ID %d no encontrado", id));
  }
}