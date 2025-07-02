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

  @ExceptionHandler(CarritoNoEncontradoException.class)
  public ResponseEntity<String> handleCarritoNotFoundExceptions(CarritoNoEncontradoException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("NOT FOUND: " + e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllExceptions(Exception ex) {
    ex.printStackTrace(); // Considera usar un logger
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error interno del servidor: " + ex.getMessage());
  }
}
