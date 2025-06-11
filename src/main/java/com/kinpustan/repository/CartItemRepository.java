package com.kinpustan.repository;

import com.kinpustan.model.CartItem;
import com.kinpustan.model.CartSession;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
  List<CartItem> findByCartSession(CartSession cartSession);
}
