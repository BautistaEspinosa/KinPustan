package com.kinpustan.exception;

public class VentaNotFoundException extends BusinessException {
  public VentaNotFoundException(Long id) {
    super(String.format("Venta con ID %d no encontrada", id));
  }
}