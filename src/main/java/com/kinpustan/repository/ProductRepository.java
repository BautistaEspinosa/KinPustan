package com.kinpustan.repository;

import com.kinpustan.model.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Producto,Long> {
  List<Producto> findByCategoriaId(Long categoriaId);
  Optional<Producto> findByNombreIgnoreCase(String nombre);
  List<Producto> findByStockLessThanEqual(Integer stock);
}
