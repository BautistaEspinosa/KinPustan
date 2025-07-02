package com.kinpustan.controller;

import com.kinpustan.apidoc.LoginApiDoc;
import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;
import com.kinpustan.service.LoginService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
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
  public ResponseEntity<Map<String,String>> registrar(@Valid @RequestBody RegisterUserRequestDTO dto){
    Map<String,String> response = loginService.registrar(dto);
    String mensaje = response.get("message");
    HttpStatus status = mensaje.equalsIgnoreCase("El correo ya existe") ?
        HttpStatus.CONFLICT : HttpStatus.CREATED;

    return ResponseEntity.status(status).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDTO dto) {
    String resultado = loginService.login(dto);
    Map<String, String> response = new HashMap<>();

    if ("Usuario no existe".equals(resultado) || "Contraseña incorrecta".equals(resultado)) {
      response.put("error", resultado);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    response.put("token", resultado);
    return ResponseEntity.ok(response);
  }

}
