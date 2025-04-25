package com.kinpustan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ProductoExistenteException.class)
  public ResponseEntity<String> handleProductoExistente(ProductoExistenteException e){
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }
  @ExceptionHandler(ProductoNoEncontradoException.class)
  public ResponseEntity<String> handleProductNotFound(ProductoNoEncontradoException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
  @ExceptionHandler(CategoriaNoEncontradaException.class)
  public ResponseEntity<String> handleCategoriaNotFound(CategoriaNoEncontradaException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
  @ExceptionHandler(CategoriaExistenteException.class)
  public ResponseEntity<String> handleCategoriaExist(CategoriaExistenteException e){
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }
}
