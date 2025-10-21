package com.kinpustan.service.impl;

import com.kinpustan.domain.dto.CategoriaDTO;
import com.kinpustan.domain.entity.Categoria;
import com.kinpustan.exception.CategoriaNotFoundException;
import com.kinpustan.repository.CategoriaRepository;
import com.kinpustan.service.CategoriaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoriaServiceImpl implements CategoriaService {

  private final CategoriaRepository categoriaRepository;

  @Override
  @Transactional
  @CacheEvict(value = "categorias", allEntries = true)
  public Categoria creaCategoria(CategoriaDTO dto) {
    log.info("Creando nueva categoría: {}", dto.nombre());

    Categoria categoria = Categoria.builder()
        .nombre(dto.nombre())
        .descripcion(dto.descripcion())
        .build();

    Categoria categoriaGuardada = categoriaRepository.save(categoria);
    log.info("Categoría creada exitosamente con ID: {}", categoriaGuardada.getId());

    return categoriaGuardada;
  }

  @Override
  @Transactional
  @CacheEvict(value = "categorias", allEntries = true)
  public Categoria actualizaCategoria(Long id, CategoriaDTO dto) {
    log.info("Actualizando categoría con ID: {}", id);

    Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new CategoriaNotFoundException(id));

    categoria.setNombre(dto.nombre());
    categoria.setDescripcion(dto.descripcion());

    Categoria categoriaActualizada = categoriaRepository.save(categoria);
    log.info("Categoría actualizada exitosamente: {}", id);

    return categoriaActualizada;
  }

  @Override
  @Transactional
  @CacheEvict(value = "categorias", allEntries = true)
  public void eliminaCategoria(Long id) {
    log.info("Eliminando categoría con ID: {}", id);

    if (!categoriaRepository.existsById(id)) {
      throw new CategoriaNotFoundException(id);
    }

    categoriaRepository.deleteById(id);
    log.info("Categoría eliminada exitosamente: {}", id);
  }

  @Override
  @Cacheable(value = "categorias", key = "#id")
  public Categoria obtenerCategoriaPorId(Long id) {
    log.debug("Obteniendo categoría con ID: {}", id);
    return categoriaRepository.findById(id)
        .orElseThrow(() -> new CategoriaNotFoundException(id));
  }

  @Override
  @Cacheable(value = "categorias")
  public List<Categoria> listarCategorias() {
    log.debug("Listando todas las categorías");
    return categoriaRepository.findAll();
  }
}
