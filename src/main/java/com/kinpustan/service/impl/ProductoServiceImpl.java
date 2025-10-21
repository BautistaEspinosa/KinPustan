package com.kinpustan.service.impl;

import com.kinpustan.domain.dto.ProductoDTO;
import com.kinpustan.domain.entity.Categoria;
import com.kinpustan.domain.entity.Producto;
import com.kinpustan.exception.CategoriaNotFoundException;
import com.kinpustan.exception.ProductoNotFoundException;
import com.kinpustan.repository.CategoriaRepository;
import com.kinpustan.repository.ProductoRepository;
import com.kinpustan.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductoServiceImpl implements ProductoService {

  private final ProductoRepository productoRepository;
  private final CategoriaRepository categoriaRepository;


  @Override
  @Transactional
  @CacheEvict(value = {"productos", "productos-categoria"}, allEntries = true)
  public ProductoDTO creaProducto(ProductoDTO dto) {

    log.info("Creando nuevo producto: {}", dto.nombre());

    Categoria categoria = categoriaRepository.findById(dto.categoriaId())
        .orElseThrow(()->new CategoriaNotFoundException(dto.categoriaId()));

    Producto producto = Producto.builder()
        .nombre(dto.nombre())
        .descripcion(dto.descripcion())
        .categoria(categoria)
        .precio(dto.precio())
        .stock(dto.stock())
        .build();
    Producto productoGuardado = productoRepository.save(producto);
    log.info("Producto creado exitosamente con ID: {}", productoGuardado.getId());

    return mapToDTO(productoGuardado);
  }

  @Override
  @Transactional
  @CacheEvict(value = {"productos", "productos-categoria"}, allEntries = true)
  public ProductoDTO actualizaProducto(Long id, ProductoDTO dto) {
    log.info("Actualizando producto con ID: {}", id);

    Producto producto = productoRepository.findById(id)
        .orElseThrow(()->new ProductoNotFoundException(id));

    Categoria categoria = categoriaRepository.findById(dto.categoriaId())
            .orElseThrow(()->new CategoriaNotFoundException(dto.categoriaId()));

    producto.setNombre(dto.nombre());
    producto.setDescripcion(dto.descripcion());
    producto.setCategoria(categoria);
    producto.setPrecio(dto.precio());
    producto.setStock(dto.stock());

    Producto productoActualizado = productoRepository.save(producto);
    log.info("Producto actualizado exitosamente: {}", id);

    return mapToDTO(productoActualizado);

  }

  @Override
  @Transactional
  @CacheEvict(value = {"productos", "productos-categoria"}, allEntries = true)
  public void eliminaProducto(Long id) {
    log.info("Eliminando producto con ID: {}", id);

    if (!productoRepository.existsById(id)) {
      throw new ProductoNotFoundException(id);
    }
    productoRepository.deleteById(id);
    log.info("Producto eliminado exitosamente: {}", id);
  }

  @Override
  @Cacheable(value = "productos", key = "#id")
  public ProductoDTO obtenerProducto(Long id) {
    log.debug("Obteniendo producto con ID: {}", id);

    return productoRepository.findById(id)
       .map(this::mapToDTO)
       .orElseThrow(()-> new ProductoNotFoundException(id));

  }

  @Override
  @Cacheable(value = "productos")
  public Page<ProductoDTO> listarProductos(Pageable pageable) {
    log.debug("Listando productos con paginación: {}", pageable);
    return productoRepository.findAll(pageable)
        .map(this::mapToDTO);
  }

  @Override
  public List<ProductoDTO> listarProductos() {
    return productoRepository.findAll().stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Cacheable(value = "productos-categoria", key = "#categoriaId")
  public List<ProductoDTO> listarPorCategoria(Long categoriaId) {
    log.debug("Listando productos por categoría: {}", categoriaId);
    return productoRepository.findByCategoriaId(categoriaId)
        .stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
  }
  @Override
  public List<ProductoDTO> buscarPorNombre(String nombre) {
    log.debug("Buscando productos por nombre: {}", nombre);
    return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
        .map(this::mapToDTO)
        .toList();
  }

  @Override
  public List<ProductoDTO> buscarPorRangoPrecio(Double min, Double max) {
    log.debug("Buscando productos por rango de precio: {} - {}", min, max);
    return productoRepository.findByPrecioBetween(min, max).stream()
        .map(this::mapToDTO)
        .toList();
  }

  @Override
  public List<ProductoDTO> buscarStockBajo(Integer stock) {
    log.debug("Buscando productos con stock bajo: {}", stock);
    return productoRepository.findByStockLessThan(stock).stream()
        .map(this::mapToDTO)
        .toList();
  }

  @Override
  public List<ProductoDTO> buscarPorCategoriaYStock(Long categoriaId, Integer stock) {
    log.debug("Buscando productos por categoría {} y stock bajo {}", categoriaId, stock);
    return productoRepository.findByCategoriaAndStockBajo(categoriaId, stock).stream()
        .map(this::mapToDTO)
        .toList();
  }
  private ProductoDTO mapToDTO(Producto producto) {
    ProductoDTO dto = ProductoDTO.from(producto);
    return dto;
  }
  private Producto mapToEntity(ProductoDTO dto) {
    Producto producto = new Producto();
    producto.setId(dto.id());
    producto.setNombre(dto.nombre());
    producto.setPrecio(dto.precio());
    producto.setStock(dto.stock());

    if (dto.categoriaId() != null) {
      Categoria categoria = categoriaRepository.findById(dto.categoriaId())
          .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
      producto.setCategoria(categoria);
    }
    return producto;
  }

}
