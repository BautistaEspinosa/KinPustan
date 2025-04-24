package com.kinpustan.repository;

import com.kinpustan.model.Producto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Producto,Long> {

  Optional<Producto> findByNombreIgnoreCase(String nombre);
}
