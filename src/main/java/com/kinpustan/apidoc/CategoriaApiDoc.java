package com.kinpustan.apidoc;

import com.kinpustan.model.Categoria;
import com.kinpustan.model.Producto;
import com.kinpustan.model.dto.CategoryUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CategoriaApiDoc {

  @Operation(
      summary = "Obtiene la lista paginada de las categorías",
      responses = @ApiResponse(
          responseCode = "200",
          description = "Lista de categorias obtenida exitosamente",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<List<Categoria>> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size);

  @Operation(
      summary = "Busca una categoría por ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Categoría encontrada",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
          @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
      }
  )
  Categoria encuentraById(@PathVariable Long id);

  @Operation(
      summary = "Crea una nueva categoría",
      responses = @ApiResponse(
          responseCode = "201",
          description = "Categoría creada exitosamente",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<Categoria> create(@RequestBody @Valid Categoria categoria);

  @Operation(
      summary = "Actualiza parcialmente una categoría por ID",

      responses = @ApiResponse(
          responseCode = "200",
          description = "Categría actualizada",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<Categoria> actualizaCategoryId(@PathVariable Long id,
      @RequestBody CategoryUpdateRequestDTO requestDTO);

  @Operation(
      summary = "Actualiza parcialmente una categoría por nombre",

      responses = @ApiResponse(
          responseCode = "200",
          description = "Categoría actualizada por nombre",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
      )
  )
  ResponseEntity<Categoria> actualizaPorNombre(@PathVariable String nombre,
      @RequestBody CategoryUpdateRequestDTO requestDTO);

  @Operation(
      summary = "Elimina una categoría por ID",
      responses = @ApiResponse(
          responseCode = "204",
          description = "Categoría eliminada exitosamente"
      )
  )
  public void deleteCategory(@PathVariable Long id);
}
