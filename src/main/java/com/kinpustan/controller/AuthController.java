package com.kinpustan.controller;

import com.kinpustan.domain.dto.AuthResponseDTO;
import com.kinpustan.domain.dto.LoginRequestDTO;
import com.kinpustan.domain.dto.RegistroRequestDTO;
import com.kinpustan.domain.dto.UsuarioDTO;
import com.kinpustan.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @PostMapping("/registro")
  public ResponseEntity<UsuarioDTO> registro(@Valid @RequestBody RegistroRequestDTO registroRequest) {
    UsuarioDTO usuario = authService.registro(registroRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
  }
}
