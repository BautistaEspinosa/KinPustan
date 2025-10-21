package com.kinpustan.exception;

public class StockInsuficienteException extends BusinessException {
  public StockInsuficienteException(String producto, Integer stockDisponible, Integer cantidadSolicitada) {
    super(String.format("Stock insuficiente para '%s'. Disponible: %d, Solicitado: %d",
        producto, stockDisponible, cantidadSolicitada));
  }
}