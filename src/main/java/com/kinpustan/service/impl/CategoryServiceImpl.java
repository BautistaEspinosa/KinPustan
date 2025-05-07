package com.kinpustan.service.impl;

import com.kinpustan.exception.CategoriaExistenteException;
import com.kinpustan.exception.CategoriaNoEncontradaException;
import com.kinpustan.exception.ProductoNoEncontradoException;
import com.kinpustan.model.Categoria;
import com.kinpustan.model.dto.CategoryUpdateRequestDTO;
import com.kinpustan.repository.CategoryRepository;
import com.kinpustan.service.CategoryService;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Page<Categoria> getAllCategory(Pageable pageable) {
    logger.info("Obteniendo todas las cargerias paginadas.");
    return categoryRepository.findAll(pageable);
  }

  @Override
  public Categoria getById(Long id) {
    logger.info("Buscando producto por id: {}", id);
    return categoryRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Producto no encontrado con id: {}", id);
          return new ProductoNoEncontradoException("No se encuentra el producto con el id: " + id);
        });
  }

  @Override
  public Categoria saveCatego(Categoria categoria) {
    logger.info("Intentando guardar categoria: {}", categoria.getNombre());
    Optional<Categoria> existCatego = categoryRepository.findByNombreIgnoreCase(
        categoria.getNombre());
    if (existCatego.isPresent()) {
      logger.warn("La categoria {} ya existe", categoria.getNombre());
      throw new CategoriaExistenteException("La categoría ya existe");
    }
    Categoria categoriaSaved = categoryRepository.save(categoria);
    logger.info("La categoría {} se guardó exitosamente", categoria.getNombre());
    return categoriaSaved;
  }

  @Override
  public void deleteCategory(Long id) {
    logger.info("Elimando categoria con el id: {}", id);
    categoryRepository.deleteById(id);
    logger.info("Categoría elimanda existosamente");
  }

  @Override
  public Categoria updateById(Long id, CategoryUpdateRequestDTO requestDTO) {
    logger.info("Actualizando la categoria por id: {}", id);
    Categoria categoria = categoryRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Categoria no econtrada con el id: {}", id);
          return new CategoriaNoEncontradaException("No se encuentra la categoria");
        });
    realizaUpdate(categoria, requestDTO);
    Categoria actualiza = categoryRepository.save(categoria);
    logger.info("Categoria actualizada: {}", actualiza);
    return actualiza;
  }

  @Override
  public Categoria updateByName(String nombre, CategoryUpdateRequestDTO requestDTO) {
    logger.info("Actualizando categoria por nombre: {}", nombre);
    Categoria existCat = categoryRepository.findByNombreIgnoreCase(nombre.trim())
        .orElseThrow(() -> {
          logger.warn("Categoria no encontrada con el nombre: {}", nombre);
          return new CategoriaNoEncontradaException("No se encuentra la categoria ");
        });
    realizaUpdate(existCat, requestDTO);
    Categoria categoryActual = categoryRepository.save(existCat);
    logger.info("Categoria actualizada: {}", categoryActual);
    return categoryActual;
  }

  private void realizaUpdate(Categoria categoria, CategoryUpdateRequestDTO requestDTO) {
    logger.debug("Aplicando actualización a la categoria: {}", categoria.getNombre());
    if (Objects.nonNull(requestDTO.getNombre())) {
      logger.debug("Actualizando el nombre de la categoria: {}", requestDTO.getNombre());
      categoria.setNombre(requestDTO.getNombre());
    }
  }
}
