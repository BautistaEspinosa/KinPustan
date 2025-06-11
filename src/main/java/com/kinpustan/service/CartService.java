package com.kinpustan.service;

import com.kinpustan.model.dto.AddToCartDTO;
import com.kinpustan.model.dto.CartSumaryDTO;

public interface CartService {

  Long createSession();

  CartSumaryDTO addOrUpdateItem(AddToCartDTO dto);

  CartSumaryDTO removeItem(Long cartSessionId, Long itemId);

  CartSumaryDTO getSummary(Long cartSessionId);

  Long checkout(Long cartSessionId, String paymentMethod);
}
