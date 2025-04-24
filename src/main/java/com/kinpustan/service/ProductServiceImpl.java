package com.kinpustan.service;

import com.kinpustan.exception.ProductoExistenteException;
import com.kinpustan.exception.ProductoNoEncontradoException;
import com.kinpustan.model.Producto;
import com.kinpustan.model.dto.ProductUpdateRequestDTO;
import com.kinpustan.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Page<Producto> getAllProds(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @Override
  public Producto getById(Long id) {
    return productRepository.findById(id).
        orElseThrow(() -> new ProductoNoEncontradoException("No se encuentra el producto con el id: "+id));
  }

  @Override
  public Producto saveProd(Producto producto) {
    Optional<Producto> existe = productRepository.findByNombreIgnoreCase(producto.getNombre());
    if (existe.isPresent()) {
      throw new ProductoExistenteException("Éste producto ya existe");
    }
    return productRepository.save(producto);
  }

  @Override
  public Producto updatebyId(Long id, ProductUpdateRequestDTO requestDTO) {
    Producto producto = productRepository.findById(id)
        .orElseThrow(() -> new ProductoNoEncontradoException("No se encuentra el producto"));
    aplicaUpdate(producto, requestDTO);
    return productRepository.save(producto);
  }

  @Override
  public Producto updatebyName(String nombre, ProductUpdateRequestDTO requestDTO) {
    Producto existeProd = productRepository.findByNombreIgnoreCase(nombre.trim()).orElseThrow(
        () -> new ProductoNoEncontradoException("No se encuentra el producto"));
    aplicaUpdate(existeProd, requestDTO);
    return productRepository.save(existeProd);
  }

  @Override
  public void DeleteProd(Long id) {
    productRepository.deleteById(id);
  }

  private void aplicaUpdate(Producto producto, ProductUpdateRequestDTO requestDTO) {
    if (requestDTO.getNombre() != null) {
      producto.setNombre(requestDTO.getNombre());
    }
    if (requestDTO.getDescripcion() != null) {
      producto.setDescripcion(requestDTO.getDescripcion());
    }
    if (requestDTO.getPrecio() != null) {
      producto.setPrecio(requestDTO.getPrecio());
    }
    if (requestDTO.getStock() != null) {
      producto.setStock(requestDTO.getStock());
    }
  }
}
