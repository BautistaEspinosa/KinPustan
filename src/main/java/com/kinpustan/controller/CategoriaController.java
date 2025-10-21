package com.kinpustan.controller;

import com.kinpustan.domain.dto.CategoriaDTO;
import com.kinpustan.domain.entity.Categoria;
import com.kinpustan.service.CategoriaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

  private final CategoriaService categoriaService;

  public CategoriaController(CategoriaService categoriaService) {
    this.categoriaService = categoriaService;
  }

  @PostMapping
  public ResponseEntity<Categoria> creaCategoria(@Valid @RequestBody CategoriaDTO dto){
    Categoria categoriaNueva = categoriaService.creaCategoria(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(categoriaNueva);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Categoria> actualizaCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto){
    Categoria categoriaActualizado = categoriaService.actualizaCategoria(id,dto);
    return ResponseEntity.status(HttpStatus.OK).body(categoriaActualizado);
  }

  @DeleteMapping("/{id}")
  public void eliminaCategoria(@PathVariable Long id){
    categoriaService.eliminaCategoria(id);
  }
  @GetMapping("/{id}")
  public Categoria obtenerCategoria(@PathVariable Long id){
    return categoriaService.obtenerCategoriaPorId(id);
  }
  @GetMapping
  public List<Categoria> listarCategorias(){
    return categoriaService.listarCategorias();
  }
}
