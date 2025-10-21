package com.kinpustan.controller;

import com.kinpustan.domain.dto.VentaRequestDTO;
import com.kinpustan.domain.dto.VentaResponseDTO;
import com.kinpustan.domain.entity.Venta;
import com.kinpustan.service.VentaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

  private final VentaService ventaService;

  @PostMapping
  public ResponseEntity<VentaResponseDTO> registraVenta(@Valid @RequestBody VentaRequestDTO ventaDTO){
    VentaResponseDTO venta = ventaService.registraVenta(ventaDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(venta);
  }

  @GetMapping
  public ResponseEntity<Page<VentaResponseDTO>> listarVentasPaginadas(
      @PageableDefault(size = 20, sort = "fechaVenta") Pageable pageable
  ) {
    return ResponseEntity.ok(ventaService.listarVentas(pageable));
  }
  @GetMapping("/all")
  public ResponseEntity<List<Venta>> listarVentas(){
    return ResponseEntity.ok(ventaService.listarVentas());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Venta> obtenerVenta(@PathVariable Long id){
    return ResponseEntity.ok(ventaService.obtenerVenta(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarVentas(@PathVariable Long id){
    ventaService.eliminarVenta(id);
    return ResponseEntity.noContent().build();
  }
}
