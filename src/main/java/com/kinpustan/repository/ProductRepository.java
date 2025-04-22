package com.kinpustan.repository;

import com.kinpustan.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Producto,Long> {

}
