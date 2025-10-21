package com.kinpustan.service;

import com.kinpustan.domain.dto.AuthResponseDTO;
import com.kinpustan.domain.dto.LoginRequestDTO;
import com.kinpustan.domain.dto.RegistroRequestDTO;
import com.kinpustan.domain.dto.UsuarioDTO;
import java.util.List;

public interface AuthService {
  AuthResponseDTO login(LoginRequestDTO loginRequest);
  UsuarioDTO registro(RegistroRequestDTO registroRequest);
  List<UsuarioDTO> listarUsuarios();
  UsuarioDTO obtenerUsuarioPorId(Long id);
  void eliminarUsuario(Long id);
  UsuarioDTO actualizarUsuario(Long id, RegistroRequestDTO usuarioActualizado);

}
