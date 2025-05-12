package com.kinpustan.controller;

import com.kinpustan.model.dto.VentaRequestDTO;
import com.kinpustan.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ventas")
public class VentaController {

  private VentaService ventaService;

  public VentaController(VentaService ventaService) {
    this.ventaService = ventaService;
  }

  @PostMapping("/vender")
  public ResponseEntity<String> registrarVenta(@Valid @RequestBody VentaRequestDTO ventaRequestDTO){
    try {
      String respuesta = ventaService.registraVenta(ventaRequestDTO);
      return ResponseEntity.ok(respuesta);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

}
