package com.metaphorce.shop_all.services.interfaces;

import com.metaphorce.shop_all.domain.*;

import java.util.List;

public interface CartService {

    void addProduct(AddCartRequest request);

    CartResponse getCart(Long userId);

    List<CartDetailsResponse> getDetailsCart(Long userId);

    void deleteProduct(Long productId, Long userId);

    SaleResponse shopProducts(SaleRequest saleRequest);
}
