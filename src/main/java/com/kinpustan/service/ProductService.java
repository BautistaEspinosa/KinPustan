package com.kinpustan.service;

import com.kinpustan.model.Producto;
import com.kinpustan.model.dto.ProductUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
  Page<Producto> getAllProds(Pageable pageable);
  Producto getById(Long id);
  Producto saveProd(Producto producto);
  void deleteProd(Long id);
  Producto updatebyId(Long id, ProductUpdateRequestDTO requestDTO);
  Producto updatebyName(String nombre,ProductUpdateRequestDTO requestDTO);
}
