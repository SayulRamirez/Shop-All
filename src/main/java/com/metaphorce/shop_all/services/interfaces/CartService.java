package com.metaphorce.shop_all.services.interfaces;

import com.metaphorce.shop_all.domain.CartResponse;
import com.metaphorce.shop_all.domain.CartDetailsResponse;
import com.metaphorce.shop_all.domain.SaleRequest;

import java.util.List;

public interface CartService {

    void addProduct(Long productId, Long userId);

    CartResponse getCart(Long userId);

    List<CartDetailsResponse> getDetailsCart(Long userId);

    void deleteProduct(Long productId, Long userId);

    void shopProducts(SaleRequest saleRequest);
}
