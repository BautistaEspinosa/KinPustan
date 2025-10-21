package com.kinpustan.controller;

import com.kinpustan.domain.dto.ProductoDTO;
import com.kinpustan.domain.entity.Producto;
import com.kinpustan.service.ProductoService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

  private final ProductoService productoService;


  @PostMapping
  public ResponseEntity<ProductoDTO> creaProducto(@Valid @RequestBody ProductoDTO dto) {
    ProductoDTO producto = productoService.creaProducto(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(producto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductoDTO> actualizaProducto(
      @PathVariable Long id,
      @Valid @RequestBody ProductoDTO dto
  ) {
    return ResponseEntity.ok(productoService.actualizaProducto(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminaProducto(@PathVariable Long id) {
    productoService.eliminaProducto(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable Long id) {
    return ResponseEntity.ok(productoService.obtenerProducto(id));
  }

  @GetMapping
  public ResponseEntity<Page<ProductoDTO>> listarProductosPaginados(
      @PageableDefault(size = 20, sort = "nombre") Pageable pageable
  ) {
    return ResponseEntity.ok(productoService.listarProductos(pageable));
  }
  @GetMapping
  public ResponseEntity<List<ProductoDTO>> listarProductos() {
    return ResponseEntity.ok(productoService.listarProductos());
  }

  @GetMapping("/categoria/{categoriaId}") //cambiarlo por nombre
  public ResponseEntity<List<ProductoDTO>> listarPorCategoria(@PathVariable Long categoriaId) {
    return ResponseEntity.ok(productoService.listarPorCategoria(categoriaId));
  }

  @GetMapping("/buscar/nombre")
  public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam String nombre) {
    return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
  }

  @GetMapping("/buscar/precio")
  public ResponseEntity<List<ProductoDTO>> buscarPorRangoPrecio(
      @RequestParam Double min,
      @RequestParam Double max
  ) {
    return ResponseEntity.ok(productoService.buscarPorRangoPrecio(min, max));
  }

  @GetMapping("/buscar/stock")
  public ResponseEntity<List<ProductoDTO>> buscarStockBajo(@RequestParam Integer stock) {
    return ResponseEntity.ok(productoService.buscarStockBajo(stock));
  }

  @GetMapping("/buscar/categoria-stock")
  public ResponseEntity<List<ProductoDTO>> buscarPorCategoriaYStock(
      @RequestParam Long categoriaId,
      @RequestParam Integer stock
  ) {
    return ResponseEntity.ok(productoService.buscarPorCategoriaYStock(categoriaId, stock));
  }

}
