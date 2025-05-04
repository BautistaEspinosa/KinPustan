package com.kinpustan.service;

import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;

public interface LoginService {

  String registrar(RegisterUserRequestDTO dto);
  String login(LoginRequestDTO requestDTO);
}
