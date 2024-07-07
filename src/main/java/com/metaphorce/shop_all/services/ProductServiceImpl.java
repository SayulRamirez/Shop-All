package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.ProductResponse;
import com.metaphorce.shop_all.domain.RestockRequest;
import com.metaphorce.shop_all.entities.Product;
import com.metaphorce.shop_all.repositories.ProductRepository;
import com.metaphorce.shop_all.services.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

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

    public void restock(RestockRequest request) {

        Product product = productRepository.findByCode(request.code())
                .orElseThrow(() -> new EntityNotFoundException("The product was not found with the code: " + request.code()));

        product.setStock(product.getStock() + request.stock());

        productRepository.save(product);
    }
}
