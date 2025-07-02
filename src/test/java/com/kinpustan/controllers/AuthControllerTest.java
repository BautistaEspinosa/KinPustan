package com.kinpustan.controllers;

import static org.mockito.Mockito.times;

import com.kinpustan.controller.AuthController;
import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;
import com.kinpustan.service.LoginService;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class AuthControllerTest {

  @Mock
  private LoginService loginService;

  @InjectMocks
  private AuthController authController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testRegistrarSuccess() {
    RegisterUserRequestDTO dto = new RegisterUserRequestDTO();
    dto.setNombre("TestUser");
    dto.setCorreo("test@example.com");
    dto.setContrasenia("Password123@");
    dto.setRoles(Set.of("USER"));

    Map<String, String> expectedResponse = Map.of("message", "Usuario registrado exitosamente.");

    Mockito.when(loginService.registrar(dto)).thenReturn(expectedResponse);

    ResponseEntity<Map<String, String>> response = authController.registrar(dto);

    Assertions.assertEquals(201, response.getStatusCode().value());
    Assertions.assertEquals("Usuario registrado exitosamente.", response.getBody().get("message"));
    Mockito.verify(loginService, times(1)).registrar(dto);
  }

/*
  @Test
  void testLoginSuccess(){
    LoginRequestDTO dto = new LoginRequestDTO();
    dto.setCorreo("test@example.com");
    dto.setContrasenia("password125");
    Map<String,String> expectedResponse = Map.of("message","login exitoso");
    Mockito.when(loginService.login(dto)).thenReturn(expectedResponse);

    ResponseEntity<Map<String,String>> response = authController.login(dto);

    Assertions.assertEquals(200,response.getStatusCode().value());
    Assertions.assertEquals("JWT-TOKEN-FAKE",response.getBody());
    Mockito.verify(loginService,times(1)).login(dto);
  }*/

}
