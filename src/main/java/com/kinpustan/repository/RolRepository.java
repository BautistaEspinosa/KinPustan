package com.kinpustan.repository;

import com.kinpustan.model.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,Long> {

  Optional<Rol> findByNombre(String nombre);
}
