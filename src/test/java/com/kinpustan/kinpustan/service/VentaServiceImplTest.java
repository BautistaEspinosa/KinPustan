package com.kinpustan.kinpustan.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

import com.kinpustan.domain.dto.DetalleVentaRequestDTO;
import com.kinpustan.domain.dto.VentaRequestDTO;
import com.kinpustan.domain.dto.VentaResponseDTO;
import com.kinpustan.domain.entity.Categoria;
import com.kinpustan.domain.entity.Producto;
import com.kinpustan.domain.entity.Venta;
import com.kinpustan.exception.ProductoNotFoundException;
import com.kinpustan.exception.StockInsuficienteException;
import com.kinpustan.repository.ProductoRepository;
import com.kinpustan.repository.VentaRepository;
import com.kinpustan.service.impl.VentaServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
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
public class VentaServiceImplTest {

  @Mock
  private VentaRepository ventaRepository;

  @Mock
  private ProductoRepository productoRepository;

  @InjectMocks
  private VentaServiceImpl ventaService;

      private Producto producto;
  private Categoria categoria;
  private VentaRequestDTO ventaRequestDTO;

  @BeforeEach
  void setUp(){
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

    DetalleVentaRequestDTO detalleVentaRequestDTO = new DetalleVentaRequestDTO(1L,2);
    ventaRequestDTO = new VentaRequestDTO(1L, LocalDateTime.now(), List.of(detalleVentaRequestDTO));
  }

  @Test
  void deberiaRegistrarVentaExitosamente(){
    //Given
    Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
    Mockito.when(productoRepository.save(any(Producto.class))).thenReturn(producto);
    Mockito.when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation ->{
      Venta venta = invocation.getArgument(0);
      venta.setId(1L);
      return venta;
    });

    //When
    VentaResponseDTO responseDTO = ventaService.registraVenta(ventaRequestDTO);

    //Then
    Assertions.assertNotNull(responseDTO);
    Assertions.assertEquals(1L,responseDTO.id());
    Assertions.assertEquals(200.0,responseDTO.total());
    Assertions.assertEquals(1,responseDTO.detalles().size());

    Mockito.verify(productoRepository).findById(1L);
    Mockito.verify(productoRepository).save(producto);
    Mockito.verify(ventaRepository).save(any(Venta.class));

    //Verificar que se actualizó el stock
    Assertions.assertEquals(8,producto.getStock());
  }

  @Test
  void deberiaLanzarExcepcionCuandoProductoNoExiste() {
    // Given
   Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.empty());

    // When & Then
   Assertions.assertThrows(ProductoNotFoundException.class, () ->
        ventaService.registraVenta(ventaRequestDTO));

   Mockito.verify(productoRepository).findById(1L);
   Mockito.verify(ventaRepository, never()).save(any(Venta.class));
  }

  @Test
  void deberiaLanzarExcepcionCuandoStockInsuficiente() {
    // Given
    producto.setStock(1); // Stock menor a la cantidad solicitada (2)
  Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

    // When & Then
   Assertions.assertThrows(StockInsuficienteException.class, () ->
        ventaService.registraVenta(ventaRequestDTO));

  Mockito.verify(productoRepository).findById(1L);
  Mockito.verify(ventaRepository, never()).save(any(Venta.class));
  }

  @Test
  void deberiaLanzarExcepcionCuandoVentaSinDetalles() {
    // Given
    VentaRequestDTO ventaSinDetalles = new VentaRequestDTO(1L, LocalDateTime.now(), List.of());

    // When & Then
   Assertions.assertThrows(IllegalArgumentException.class, () ->
        ventaService.registraVenta(ventaSinDetalles));

   Mockito.verify(ventaRepository, never()).save(any(Venta.class));
  }
}
