package com.kinpustan.apidoc;

import com.kinpustan.model.Producto;
import com.kinpustan.model.dto.LoginRequestDTO;
import com.kinpustan.model.dto.RegisterUserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginApiDoc {

  @Operation(
      summary = "Registro de usuarios",
      responses = @ApiResponse(
          responseCode = "201",
          description = "Usuario creado exitosamente",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<Map<String,String>> registrar(@RequestBody RegisterUserRequestDTO dto);

  @Operation(
      summary = "Login de usuario",
      responses = @ApiResponse(
          responseCode = "200",
          description = "Login exitoso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<Map<String,String>> login(@RequestBody LoginRequestDTO dto);
}
