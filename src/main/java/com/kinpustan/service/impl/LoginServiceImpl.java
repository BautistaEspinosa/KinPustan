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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

  private final UsuarioRepository usuarioRepository;
  private final RolRepository rolRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public LoginServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository,
      PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.usuarioRepository = usuarioRepository;
    this.rolRepository = rolRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Override
  @Transactional
  public String registrar(RegisterUserRequestDTO dto) {
    logger.info("Intentando registrar usuario con correo: {}", dto.getCorreo());

    if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
      logger.warn("Intento de registro fallido: el correo {} ya existe", dto.getCorreo());
      return "El correo ya existe";
    }

    Set<Rol> rolSet = new HashSet<>();
    for (String nombreRol : dto.getRoles()) {
      Rol rol = rolRepository.findByNombre(nombreRol)
          .orElseGet(() -> {
            logger.info("Rol '{}' no encontrado. Creando nuevo rol.", nombreRol);
            return rolRepository.save(new Rol(nombreRol));
          });
      rolSet.add(rol);
    }

    Usuario user = new Usuario();
    user.setNombre(dto.getNombre());
    user.setCorreo(dto.getCorreo());
    user.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
    user.setRoles(rolSet);

    usuarioRepository.save(user);
    logger.info("Usuario registrado exitosamente con correo: {}", dto.getCorreo());
    return "Usuario registrado exitosamente.";
  }

  @Override
  public String login(LoginRequestDTO requestDTO) {
    logger.info("Intentando login para el correo: {}", requestDTO.getCorreo());

    Optional<Usuario> userOpt = usuarioRepository.findByCorreo(requestDTO.getCorreo());
    if (userOpt.isEmpty()) {
      logger.warn("Login fallido: usuario con correo {} no existe", requestDTO.getCorreo());
      return "Usuario no existe";
    }

    Usuario user = userOpt.get();
    boolean passCorrect = passwordEncoder.matches(requestDTO.getContrasenia(),
        user.getContrasenia());

    if (!passCorrect) {
      logger.warn("Login fallido: contraseña incorrecta para el usuario {}", requestDTO.getCorreo());
      return "Contraseña incorrecta";
    }

    String token = jwtUtil.generarToken(user.getCorreo());
    logger.info("Login exitoso para el usuario {}. Token generado.", requestDTO.getCorreo());
    return token;
  }
}
