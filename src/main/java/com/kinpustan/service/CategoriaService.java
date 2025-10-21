package com.kinpustan.service;

import com.kinpustan.domain.dto.CategoriaDTO;
import com.kinpustan.domain.entity.Categoria;
import java.util.List;

public interface CategoriaService {
  Categoria creaCategoria(CategoriaDTO dto);
  Categoria actualizaCategoria(Long id, CategoriaDTO dto);
  void eliminaCategoria(Long id);
  Categoria obtenerCategoriaPorId(Long id);
  List<Categoria> listarCategorias();
}
