package com.kinpustan.repository;

import com.kinpustan.domain.entity.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

  //Buscar productos por categoria
  List<Producto> findByCategoriaId(Long categoriaId);
  // Buscar productos por nombre que contengan el texto
  List<Producto> findByNombreContainingIgnoreCase(String nombre);

  // Buscar productos dentro de un rango de precios
  List<Producto> findByPrecioBetween(Double min, Double max);

  // Buscar productos con stock menor a cierto valor
  List<Producto> findByStockLessThan(Integer stock);

  // Ejemplo de búsqueda combinada (categoría + stock bajo)
  @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.stock < :stock")
  List<Producto> findByCategoriaAndStockBajo(Long categoriaId, Integer stock);
}
