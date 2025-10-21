package com.kinpustan.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    log.warn("Intento de login fallido: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        "CREDENCIALES_INCORRECTAS",
        "Username o contraseña incorrectos",
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler({ProductoNotFoundException.class, CategoriaNotFoundException.class, VentaNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFound(BusinessException ex) {
    log.warn("Recurso no encontrado: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        "RECURSO_NO_ENCONTRADO",
        ex.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(StockInsuficienteException.class)
  public ResponseEntity<ErrorResponse> handleStockInsuficiente(StockInsuficienteException ex) {
    log.warn("Stock insuficiente: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        "STOCK_INSUFICIENTE",
        ex.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
    log.error("Error de negocio: {}", ex.getMessage(), ex);
    ErrorResponse error = new ErrorResponse(
        "ERROR_NEGOCIO",
        ex.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
    log.error("Error interno: {}", ex.getMessage(), ex);
    ErrorResponse error = new ErrorResponse(
        "ERROR_INTERNO",
        "Ha ocurrido un error interno en el servidor",
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex
  ) {
    log.warn("Errores de validación: {}", ex.getMessage());
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.badRequest().body(errors);
  }

  public record ErrorResponse(String codigo, String mensaje, LocalDateTime timestamp) {}
}
