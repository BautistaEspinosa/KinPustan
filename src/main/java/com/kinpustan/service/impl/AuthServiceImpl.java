package com.kinpustan.service.impl;

import com.kinpustan.domain.dto.AuthResponseDTO;
import com.kinpustan.domain.dto.LoginRequestDTO;
import com.kinpustan.domain.dto.RegistroRequestDTO;
import com.kinpustan.domain.dto.UsuarioDTO;
import com.kinpustan.domain.entity.Usuario;
import com.kinpustan.repository.UsuarioRepository;
import com.kinpustan.security.JwtService;
import com.kinpustan.service.AuthService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthResponseDTO login(LoginRequestDTO loginRequest) {
   authenticationManager.authenticate(
       new UsernamePasswordAuthenticationToken(
           loginRequest.username(),
           loginRequest.password()
       )
   );

    var usuario = usuarioRepository.findByUsername(loginRequest.username())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    var jwtToken = jwtService.generateToken(usuario);

    return new AuthResponseDTO(
        jwtToken,
        usuario.getId(),
        usuario.getUsername(),
        usuario.getEmail(),
        usuario.getNombre(),
        usuario.getApellido(),
        usuario.getRol()
    );
  }

  @Override
  public UsuarioDTO registro(RegistroRequestDTO registroRequest) {
    if (usuarioRepository.existsByUsername(registroRequest.username())) {
      throw new RuntimeException("El username ya existe");
    }

    if (usuarioRepository.existsByEmail(registroRequest.email())) {
      throw new RuntimeException("El email ya está registrado");
    }

    var usuario = Usuario.builder()
        .username(registroRequest.username())
        .email(registroRequest.email())
        .password(passwordEncoder.encode(registroRequest.password()))
        .nombre(registroRequest.nombre())
        .apellido(registroRequest.apellido())
        .rol(registroRequest.rol())
        .activo(true)
        .build();

    var usuarioGuardado = usuarioRepository.save(usuario);
    return mapToDTO(usuarioGuardado);
  }

  @Override
  public List<UsuarioDTO> listarUsuarios() {
    return usuarioRepository.findAll().stream()
        .map(this::mapToDTO)
        .toList();
  }

  @Override
  public UsuarioDTO obtenerUsuarioPorId(Long id) {
    var usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    return mapToDTO(usuario);
  }

  @Override
  public void eliminarUsuario(Long id) {
    if (!usuarioRepository.existsById(id)) {
      throw new RuntimeException("Usuario no encontrado");
    }
    usuarioRepository.deleteById(id);
  }

  @Override
  public UsuarioDTO actualizarUsuario(Long id, RegistroRequestDTO usuarioActualizado) {
    var usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Verificar si el username ya existe (excepto para el usuario actual)
    if (!usuario.getUsername().equals(usuarioActualizado.username()) &&
        usuarioRepository.existsByUsername(usuarioActualizado.username())) {
      throw new RuntimeException("El username ya existe");
    }

    // Verificar si el email ya existe (excepto para el usuario actual)
    if (!usuario.getEmail().equals(usuarioActualizado.email()) &&
        usuarioRepository.existsByEmail(usuarioActualizado.email())) {
      throw new RuntimeException("El email ya está registrado");
    }

    usuario.setUsername(usuarioActualizado.username());
    usuario.setEmail(usuarioActualizado.email());
    usuario.setNombre(usuarioActualizado.nombre());
    usuario.setApellido(usuarioActualizado.apellido());
    usuario.setRol(usuarioActualizado.rol());

    // Solo actualizar la contraseña si se proporciona una nueva
    if (usuarioActualizado.password() != null && !usuarioActualizado.password().isEmpty()) {
      usuario.setPassword(passwordEncoder.encode(usuarioActualizado.password()));
    }

    var usuarioGuardado = usuarioRepository.save(usuario);
    return mapToDTO(usuarioGuardado);
  }

  private UsuarioDTO mapToDTO(Usuario usuario) {
    return new UsuarioDTO(
        usuario.getId(),
        usuario.getUsername(),
        usuario.getEmail(),
        usuario.getNombre(),
        usuario.getApellido(),
        usuario.getRol(),
        usuario.getFechaCreacion(),
        usuario.getActivo()
    );
  }
}
