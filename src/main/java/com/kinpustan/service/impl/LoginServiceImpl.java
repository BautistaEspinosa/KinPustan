package com.kinpustan.service.impl;

import com.kinpustan.model.Rol;
import com.kinpustan.model.Usuario;
import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;
import com.kinpustan.repository.RolRepository;
import com.kinpustan.repository.UsuarioRepository;
import com.kinpustan.security.JwtUtil;
import com.kinpustan.service.LoginService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  private final UsuarioRepository usuarioRepository;
  private final RolRepository rolRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public LoginServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository,
      PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
    this.usuarioRepository = usuarioRepository;
    this.rolRepository = rolRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Override
  @Transactional
  public String registrar(RegisterUserRequestDTO dto) {
    if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
      return "El correo ya existe";
    }
    Set<Rol> rolSet = new HashSet<>();
    for (String nombreRol : dto.getRoles()) {
      Rol rol = rolRepository.findByNombre(nombreRol)
          .orElseGet(() -> rolRepository.save(new Rol(nombreRol)));
      rolSet.add(rol);
    }
    Usuario user = new Usuario();
    user.setNombre(dto.getNombre());
    user.setCorreo(dto.getCorreo());
    user.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
    user.setRoles(rolSet);

    usuarioRepository.save(user);
    return "Usuario registrado exitosamente.";
  }

  @Override
  public String login(LoginRequestDTO requestDTO) {
    Optional<Usuario> userOpt = usuarioRepository.findByCorreo(requestDTO.getCorreo());
    if (userOpt.isEmpty()) {
      return "Usuario no existe";
    }
    Usuario user = userOpt.get();
    boolean passCorrect = passwordEncoder.matches(requestDTO.getContrasenia(),
        user.getContrasenia());
    if (!passCorrect) {
      return "Contraseña incorrecta";
    }
    String token = jwtUtil.generarToken(user.getCorreo());

    return token;
  }
}
