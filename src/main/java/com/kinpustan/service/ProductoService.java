package com.kinpustan.service;

import com.kinpustan.domain.dto.ProductoDTO;
import com.kinpustan.domain.entity.Producto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {

  ProductoDTO creaProducto(ProductoDTO dto);
  ProductoDTO actualizaProducto(Long id, ProductoDTO dto);
  void eliminaProducto(Long id);
  ProductoDTO obtenerProducto(Long id);
  List<ProductoDTO> listarProductos();
  Page<ProductoDTO> listarProductos(Pageable pageable);
  List<ProductoDTO> listarPorCategoria(Long categoriaId);
  List<ProductoDTO> buscarPorNombre(String nombre);
  List<ProductoDTO> buscarPorRangoPrecio(Double min, Double max);
  List<ProductoDTO> buscarStockBajo(Integer stock);
  List<ProductoDTO> buscarPorCategoriaYStock(Long categoriaId, Integer stock);
}
