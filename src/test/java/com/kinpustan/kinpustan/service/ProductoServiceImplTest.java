package com.kinpustan.kinpustan.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

import com.kinpustan.domain.dto.ProductoDTO;
import com.kinpustan.domain.entity.Categoria;
import com.kinpustan.domain.entity.Producto;
import com.kinpustan.exception.CategoriaNotFoundException;
import com.kinpustan.exception.ProductoNotFoundException;
import com.kinpustan.repository.CategoriaRepository;
import com.kinpustan.repository.ProductoRepository;
import com.kinpustan.service.impl.ProductoServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

  @Mock
  private ProductoRepository productoRepository;

  @Mock
  private CategoriaRepository categoriaRepository;

  @InjectMocks
  private ProductoServiceImpl productoService;

  private Categoria categoria;
  private Producto producto;
  private ProductoDTO productoDTO;

  @BeforeEach
  void setUp() {
    categoria = Categoria.builder()
        .id(1L)
        .nombre("Categoría Test")
        .descripcion("Descripción test")
        .build();

    producto = Producto.builder()
        .id(1L)
        .nombre("Producto Test")
        .descripcion("Descripción test")
        .precio(100.0)
        .stock(10)
        .categoria(categoria)
        .build();

    productoDTO = new ProductoDTO(
        null, "Producto Test", "Descripción test", 1L, "Categoría Test", 100.0, 10
    );
  }

  @Test
  void deberiaCrearProductoExitosamente() {
    // Given
    Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
    Mockito.when(productoRepository.save(any(Producto.class))).thenReturn(producto);

    // Mockito.when
    ProductoDTO resultado = productoService.creaProducto(productoDTO);

    // Then
    Assertions.assertNotNull(resultado);
    Assertions.assertEquals("Producto Test", resultado.nombre());
    Assertions.assertEquals(100.0, resultado.precio());
    Assertions.assertEquals(10, resultado.stock());
    Assertions.assertEquals(1L, resultado.categoriaId());

    Mockito.verify(categoriaRepository).findById(1L);
    Mockito.verify(productoRepository).save(any(Producto.class));
  }

  @Test
  void deberiaLanzarExcepcionCuandoCategoriaNoExiste() {
    // Given
    Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

    // Mockito.when & Then
    Assertions.assertThrows(CategoriaNotFoundException.class, () ->
        productoService.creaProducto(productoDTO));

    Mockito.verify(categoriaRepository).findById(1L);
    Mockito.verify(productoRepository, never()).save(any(Producto.class));
  }

  @Test
  void deberiaObtenerProductoPorId() {
    // Given
    Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

    // Mockito.when
    ProductoDTO resultado = productoService.obtenerProducto(1L);

    // Then
    Assertions.assertNotNull(resultado);
    Assertions.assertEquals(1L, resultado.id());
    Assertions.assertEquals("Producto Test", resultado.nombre());

    Mockito.verify(productoRepository).findById(1L);
  }

  @Test
  void deberiaLanzarExcepcionCuandoProductoNoExiste() {
    // Given
    Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.empty());

    // Mockito.when & Then
    Assertions.assertThrows(ProductoNotFoundException.class, () ->
        productoService.obtenerProducto(1L));

    Mockito.verify(productoRepository).findById(1L);
  }
}
