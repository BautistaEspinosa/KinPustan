package com.kinpustan.controller;

import com.kinpustan.apidoc.CategoriaApiDoc;
import com.kinpustan.model.Categoria;
import com.kinpustan.model.dto.CategoryUpdateRequestDTO;
import com.kinpustan.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/catalogs")
public class CategoriaController implements CategoriaApiDoc {

  private CategoryService categoryService;

  public CategoriaController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<Categoria>> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    List<Categoria> categorias = categoryService.getAllCategory(pageable).getContent();
    return ResponseEntity.status(HttpStatus.OK).body(categorias);
  }

  @GetMapping("/{id}")
  public Categoria encuentraById(@PathVariable Long id) {
    return categoryService.getById(id);
  }
  @PostMapping
  public ResponseEntity<Categoria> create(@RequestBody @Valid Categoria categoria){
    Categoria categoria1 = categoryService.saveCatego(categoria);
    return ResponseEntity.status(HttpStatus.CREATED).body(categoria1);
  }

  @PatchMapping("/id/{id}")
  public ResponseEntity<Categoria> actualizaCategoryId(@PathVariable Long id,
      @RequestBody CategoryUpdateRequestDTO requestDTO){
    Categoria categoriaActual = categoryService.updateById(id,requestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(categoriaActual);
  }
  @PatchMapping("/nombre/{nombre}")
  public ResponseEntity<Categoria> actualizaPorNombre(@PathVariable String nombre,
      @RequestBody CategoryUpdateRequestDTO requestDTO){
    Categoria categoriaActual = categoryService.updateByName(nombre,requestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(categoriaActual);
  }
  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable Long id){
    categoryService.deleteCategory(id);
  }
}
