package com.metaphorce.shop_all.services.interfaces;

import com.metaphorce.shop_all.domain.ProductRegisterRequest;
import com.metaphorce.shop_all.domain.ProductResponse;
import com.metaphorce.shop_all.domain.RestockRequest;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAll();

    void restock(RestockRequest request);

    ProductResponse registerProduct(ProductRegisterRequest request);
}
