package com.kinpustan.controller;

import com.kinpustan.domain.dto.RegistroRequestDTO;
import com.kinpustan.domain.dto.UsuarioDTO;
import com.kinpustan.service.AuthService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

  private final AuthService authService;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
    return ResponseEntity.ok(authService.listarUsuarios());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
    return ResponseEntity.ok(authService.obtenerUsuarioPorId(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UsuarioDTO> actualizarUsuario(
      @PathVariable Long id,
      @Valid @RequestBody RegistroRequestDTO usuarioActualizado
  ) {
    return ResponseEntity.ok(authService.actualizarUsuario(id, usuarioActualizado));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
    authService.eliminarUsuario(id);
    return ResponseEntity.noContent().build();
  }

}
