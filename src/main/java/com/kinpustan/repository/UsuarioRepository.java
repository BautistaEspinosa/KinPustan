package com.kinpustan.repository;

import com.kinpustan.domain.entity.Usuario;
import com.kinpustan.domain.enums.Rol;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

  Optional<Usuario> findByUsername(String username);
  Optional<Usuario> findByEmail(String email);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  List<Usuario> findByRol(Rol rol);
  List<Usuario> findByActivoTrue();

}
