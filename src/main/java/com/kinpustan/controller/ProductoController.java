package com.kinpustan.controller;

import com.kinpustan.model.Producto;
import com.kinpustan.repository.ProductRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductoController {

  private ProductRepository productRepository;

  public ProductoController(ProductRepository productRepository){
    this.productRepository = productRepository;
  }

  @GetMapping
  public List<Producto> getAll(){
    return productRepository.findAll();
  }

  @PostMapping
  public Producto create(@RequestBody Producto producto){
    return productRepository.save(producto);
  }
}
