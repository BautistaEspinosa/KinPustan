package com.kinpustan.service.impl;

import com.kinpustan.exception.CategoriaNoEncontradaException;
import com.kinpustan.exception.ProductoExistenteException;
import com.kinpustan.exception.ProductoNoEncontradoException;
import com.kinpustan.model.Categoria;
import com.kinpustan.model.Producto;
import com.kinpustan.model.dto.ProductUpdateRequestDTO;
import com.kinpustan.repository.ProductRepository;
import com.kinpustan.service.CategoryService;
import com.kinpustan.service.ProductService;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
  private final ProductRepository productRepository;
  private final CategoryService categoryService;

  public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
  }

  @Override
  public Page<Producto> getAllProds(Pageable pageable) {
    logger.info("Obteniendo todos los productos paginados");
    return productRepository.findAll(pageable);
  }

  @Override
  public Producto getById(Long id) {
    logger.info("Buscando producto por id: {}", id);
    return productRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Producto no encontrado con id: {}", id);
          return new ProductoNoEncontradoException("No se encuentra el producto con el id: " + id);
        });
  }

  @Override
  @Transactional
  public Producto saveProd(Producto producto) {
    logger.info("Intentando guardar producto: {}", producto.getNombre());

    productRepository.findByNombreIgnoreCase(producto.getNombre())
        .ifPresent(p -> {
          logger.warn("Producto ya existente con nombre: {}", producto.getNombre());
          throw new ProductoExistenteException("Éste producto ya existe");
        });

    if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
      logger.warn("Categoría no especificada correctamente en el producto");
      throw new CategoriaNoEncontradaException("Debes especificar una categoría existente");
    }

    try {
      categoryService.getById(producto.getCategoria().getId());
    } catch (Exception e) {
      logger.error("Categoría no encontrada con ID: {}", producto.getCategoria().getId());
      throw new CategoriaNoEncontradaException("La categoría especificada no existe");
    }

    Producto guardado = productRepository.save(producto);
    logger.info("Producto guardado exitosamente: {}", producto.getNombre());
    return guardado;
  }

  @Override
  @Transactional
  public Producto updatebyId(Long id, ProductUpdateRequestDTO requestDTO) {
    logger.info("Actualizando producto por id: {}", id);
    Producto producto = productRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Producto no encontrado con id: {}", id);
          return new ProductoNoEncontradoException("No se encuentra el producto");
        });

    aplicaUpdate(producto, requestDTO);
    Producto actualizado = productRepository.save(producto);
    logger.info("Producto actualizado: {}", actualizado);
    return actualizado;
  }

  @Override
  @Transactional
  public Producto updatebyName(String nombre, ProductUpdateRequestDTO requestDTO) {
    logger.info("Actualizando producto por nombre: {}", nombre);
    Producto producto = productRepository.findByNombreIgnoreCase(nombre.trim())
        .orElseThrow(() -> {
          logger.warn("Producto no encontrado con nombre: {}", nombre);
          return new ProductoNoEncontradoException("No se encuentra el producto");
        });

    aplicaUpdate(producto, requestDTO);
    Producto actualizado = productRepository.save(producto);
    logger.info("Producto actualizado: {}", actualizado);
    return actualizado;
  }

  @Override
  public void DeleteProd(Long id) {
    logger.info("Eliminando producto por id: {}", id);
    if (!productRepository.existsById(id)) {
      logger.warn("Intento de eliminar producto inexistente con id: {}", id);
      throw new ProductoNoEncontradoException("No se encuentra el producto con id: " + id);
    }
    productRepository.deleteById(id);
    logger.info("Producto eliminado con éxito");
  }

  private void aplicaUpdate(Producto producto, ProductUpdateRequestDTO requestDTO) {
    logger.debug("Aplicando actualización al producto: {}", producto.getId());

    if (requestDTO.getNombre() != null &&
        !requestDTO.getNombre().equalsIgnoreCase(producto.getNombre())) {
      productRepository.findByNombreIgnoreCase(requestDTO.getNombre())
          .ifPresent(p -> {
            logger.warn("Ya existe un producto con el nombre: {}", requestDTO.getNombre());
            throw new ProductoExistenteException("Ya existe un producto con ese nombre");
          });
      logger.debug("Actualizando nombre: {}", requestDTO.getNombre());
      producto.setNombre(requestDTO.getNombre());
    }

    if (requestDTO.getDescripcion() != null) {
      logger.debug("Actualizando descripción: {}", requestDTO.getDescripcion());
      producto.setDescripcion(requestDTO.getDescripcion());
    }

    if (requestDTO.getPrecio() != null) {
      logger.debug("Actualizando precio: {}", requestDTO.getPrecio());
      producto.setPrecio(requestDTO.getPrecio());
    }

    if (requestDTO.getStock() != null) {
      logger.debug("Actualizando stock: {}", requestDTO.getStock());
      producto.setStock(requestDTO.getStock());
    }

    if (Objects.nonNull(requestDTO.getCategoriaId())) {
      try {
        Categoria categoria = categoryService.getById(requestDTO.getCategoriaId());
        producto.setCategoria(categoria);
      } catch (CategoriaNoEncontradaException e) {
        logger.error("Categoría no encontrada con ID: {}", requestDTO.getCategoriaId());
        throw e;
      }
    }
  }
}
