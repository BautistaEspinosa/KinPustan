package com.kinpustan.repository;

import com.kinpustan.model.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categoria,Long> {
Optional<Categoria> findByNombreIgnoreCase(String nombre);
}
