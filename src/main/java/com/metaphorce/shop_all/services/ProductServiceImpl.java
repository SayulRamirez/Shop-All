package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.ProductResponse;
import com.metaphorce.shop_all.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAll() {

        List<ProductResponse> response = new ArrayList<>();

        productRepository.findAll().forEach(
                product -> response.add(new ProductResponse(
                        product.getDescription(),
                        product.getCategory(),
                        product.getStock(),
                        product.getPrice())));

        return response;
    }
}
