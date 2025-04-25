package com.kinpustan.apidoc;

import com.kinpustan.model.Producto;
import com.kinpustan.model.dto.ProductUpdateRequestDTO;
import com.kinpustan.model.dto.ProductoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductoApiDoc {

  @Operation(
      summary = "Obtiene la lista paginada de productos",
      responses = @ApiResponse(
          responseCode = "200",
          description = "Lista de productos obtenida exitosamente",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<List<Producto>> getAll(@RequestParam int page, @RequestParam int size);

  @Operation(
      summary = "Busca un producto por ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Producto encontrado",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
          @ApiResponse(responseCode = "404", description = "Producto no encontrado")
      }
  )
  ProductoResponseDTO findByID(@PathVariable Long id);

  @Operation(
      summary = "Crea un nuevo producto",
      responses = @ApiResponse(
          responseCode = "201",
          description = "Producto creado exitosamente",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<ProductoResponseDTO> create(@RequestBody Producto producto);

  @Operation(
      summary = "Actualiza parcialmente un producto por ID",

      responses = @ApiResponse(
          responseCode = "200",
          description = "Producto actualizado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  public ResponseEntity<Producto> actualizaProdId(@PathVariable Long id,
      @RequestBody ProductUpdateRequestDTO requestDTO);

  @Operation(
      summary = "Actualiza parcialmente un producto por nombre",

      responses = @ApiResponse(
          responseCode = "200",
          description = "Producto actualizado por nombre",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  public ResponseEntity<Producto> actualizaPorNombre(@PathVariable String nombre,
      @RequestBody ProductUpdateRequestDTO requestDTO);

  @Operation(
      summary = "Elimina un producto por ID",
      responses = @ApiResponse(
          responseCode = "204",
          description = "Producto eliminado exitosamente"
      )
  )
  void DeleteProds(@PathVariable Long id);
  @Operation(summary = "Obtener productos por categoría")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Productos encontrados"),
      @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
  })
  ResponseEntity<List<Producto>> getByCategoria(@PathVariable Long categoriaId);
}
