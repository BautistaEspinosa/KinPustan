package com.kinpustan.controller;

import com.kinpustan.apidoc.ProductoApiDoc;
import com.kinpustan.model.Categoria;
import com.kinpustan.model.Producto;
import com.kinpustan.model.dto.ProductUpdateRequestDTO;
import com.kinpustan.model.dto.ProductoResponseDTO;
import com.kinpustan.repository.ProductRepository;
import com.kinpustan.service.CategoryService;
import com.kinpustan.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductoController implements ProductoApiDoc {

  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  public ProductoController(ProductService productService) {
    this.productService = productService;
  }

  //Obtiene todos los productos existentes en stock
  @GetMapping
  public ResponseEntity<List<Producto>> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    List<Producto> prods = productService.getAllProds(pageable).getContent();
    return ResponseEntity.status(HttpStatus.OK).body(prods);
  }

  //busca un producto por id
  @GetMapping("/{id}")
  public ProductoResponseDTO findByID(@PathVariable Long id) {
    Producto producto = productService.getById(id);
    return new ProductoResponseDTO(producto);
  }

  //CREA UN NUEVO PRODUCTO
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductoResponseDTO> create(@RequestBody @Valid Producto producto) {
    System.out.println("Producto recibido: " + producto);

    Producto producto1 = productService.saveProd(producto);
    System.out.println("Producto guardado: " + producto1);

    ProductoResponseDTO dto = new ProductoResponseDTO(producto1);
    System.out.println("DTO creado: " + dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
  }


  @GetMapping("/categoria/{categoriaId}")
  public ResponseEntity<List<Producto>> getByCategoria(@PathVariable Long categoriaId) {
    List<Producto> productos = productRepository.findByCategoriaId(categoriaId);
    return ResponseEntity.ok(productos);
  }

  @PatchMapping("/id/{id}")
  public ResponseEntity<Producto> actualizaProdId(@PathVariable Long id,
      @RequestBody ProductUpdateRequestDTO requestDTO) {
    Producto actualizado = productService.updatebyId(id, requestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(actualizado);
  }

  @PatchMapping("/nombre/{nombre}")
  public ResponseEntity<Producto> actualizaPorNombre(@PathVariable String nombre,
      @RequestBody ProductUpdateRequestDTO requestDTO) {
    Producto actualizado = productService.updatebyName(nombre, requestDTO);
    return ResponseEntity.ok(actualizado);
  }

  @DeleteMapping("/{id}")
  public void deleteProds(@PathVariable Long id) {
    productService.deleteProd(id);
  }

  @GetMapping("/low-stock")
  public ResponseEntity<List<ProductoResponseDTO>> getLowStockProducts(){
    List<Producto> lowProducts = productRepository.findByStockLessThanEqual(5);
    List<ProductoResponseDTO> dtos = lowProducts.stream()
        .map(ProductoResponseDTO::new)
        .toList();
    return ResponseEntity.ok(dtos);
  }
}
