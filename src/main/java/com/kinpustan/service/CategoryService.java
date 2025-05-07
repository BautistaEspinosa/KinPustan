package com.kinpustan.service;

import com.kinpustan.model.Categoria;
import com.kinpustan.model.dto.CategoryUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
  Page<Categoria> getAllCategory(Pageable pageable);
  Categoria getById(Long id);
  Categoria saveCatego(Categoria categoria);
  void deleteCategory(Long id);
  Categoria updateById(Long id, CategoryUpdateRequestDTO requestDTO);
  Categoria updateByName(String nombre,CategoryUpdateRequestDTO requestDTO);
}
