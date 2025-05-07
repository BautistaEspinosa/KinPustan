package com.kinpustan.controller;

import com.kinpustan.apidoc.LoginApiDoc;
import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;
import com.kinpustan.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController implements LoginApiDoc {

  private final LoginService loginService;

  public AuthController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> registrar(@Valid @RequestBody RegisterUserRequestDTO dto){
    String response = loginService.registrar(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO dto){
    String response = loginService.login(dto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
