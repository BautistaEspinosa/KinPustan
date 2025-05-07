package com.kinpustan.controllers;

import static org.mockito.Mockito.times;

import com.kinpustan.controller.AuthController;
import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;
import com.kinpustan.service.LoginService;
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
  void testRegistrarSuccess(){
    RegisterUserRequestDTO dto = new RegisterUserRequestDTO();
    dto.setNombre("TestUser");
    dto.setContrasenia("password124");
    Mockito.when(loginService.registrar(dto)).thenReturn("JWT-TOKEN-FAKE");

    ResponseEntity<String> response = authController.registrar(dto);

    Assertions.assertEquals(201,response.getStatusCode().value());
    Assertions.assertEquals("JWT-TOKEN-FAKE", response.getBody());
    Mockito.verify(loginService, times(1)).registrar(dto);
  }

  @Test
  void testLoginSuccess(){
    LoginRequestDTO dto = new LoginRequestDTO();
    dto.setCorreo("test@example.com");
    dto.setContrasenia("password125");
    Mockito.when(loginService.login(dto)).thenReturn("JWT-TOKEN-FAKE");
    ResponseEntity<String> response = authController.login(dto);

    Assertions.assertEquals(200,response.getStatusCode().value());
    Assertions.assertEquals("JWT-TOKEN-FAKE",response.getBody());
    Mockito.verify(loginService,times(1)).login(dto);
  }

}
