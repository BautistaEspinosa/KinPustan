package com.kinpustan.service;

import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;
import java.util.Map;

public interface LoginService {

  Map<String,String> registrar(RegisterUserRequestDTO dto);
  String login(LoginRequestDTO requestDTO);
}
