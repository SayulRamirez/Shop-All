package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.*;
import com.metaphorce.shop_all.entities.Cart;
import com.metaphorce.shop_all.entities.CartDetails;
import com.metaphorce.shop_all.entities.Product;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.exceptions.NotEnoughStockException;
import com.metaphorce.shop_all.repositories.CartDetailsRepository;
import com.metaphorce.shop_all.repositories.CartRepository;
import com.metaphorce.shop_all.repositories.ProductRepository;
import com.metaphorce.shop_all.repositories.UserRepository;
import com.metaphorce.shop_all.services.interfaces.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final CartDetailsRepository cartDetailsRepository;

    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository,
                           ProductRepository productRepository, CartDetailsRepository cartDetailsRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartDetailsRepository = cartDetailsRepository;

    }

    @Transactional
    @Override
    public void addProduct(AddCartRequest request) {

        Cart cart = cartRepository.findById(request.user_id())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found whit user " + request.user_id()));

        Product product = productRepository.findById(request.product_id())
                .orElseThrow(() -> new EntityNotFoundException("Product not found whit id: " + request.product_id()));

        Integer stock = product.getStock();

        if (stock < request.pieces()) {
            throw new NotEnoughStockException("Not enough stock, only" + stock + " pieces");
        }

        Optional<Long> idCartDetails = cartDetailsRepository.getId(product.getId(), cart.getId());

        CartDetails details;

        if (idCartDetails.isEmpty()) {

            details = CartDetails.builder()
                    .cart(cart)
                    .product(product)
                    .numberPieces(request.pieces())
                    .amount(request.pieces() * product.getPrice()).build();

        } else {
            details = cartDetailsRepository.findById(idCartDetails.get()).orElseThrow(() -> new EntityNotFoundException("Cart details not found"));

            details.setNumberPieces(request.pieces());
            details.setAmount(request.pieces() * product.getPrice());

        }

        cartDetailsRepository.save(details);

        updateCart(cart);
    }

    @Override
    public CartResponse getCart(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found whit ID: " + userId));

        return new CartResponse(
                user.getName(),
                user.getCart().getNumberProducts(),
                user.getCart().getAmount()
        );
    }

    @Override
    public List<CartDetailsResponse> getDetailsCart(Long userId) {

        Long idCart = userRepository
                .findById(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found whit user: " + userId)).getCart().getId();

        List<CartDetailsResponse> response = new ArrayList<>();

        cartDetailsRepository.findCartDetailsByCartId(idCart).forEach(d ->
                response.add(new CartDetailsResponse(
                        d.getProduct().getDescription(),
                        d.getProduct().getCode(),
                        d.getProduct().getCategory(),
                        d.getNumberPieces(),
                        d.getAmount()
                )));

        return response;
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId, Long userId) {

        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found whit user " + userId));

        Long cartDetailsId = cartDetailsRepository.getId(productId, cart.getId())
                .orElseThrow(() -> new EntityNotFoundException("cart details not found"));

        cartDetailsRepository.deleteById(cartDetailsId);

        updateCart(cart);
    }

    private void updateCart(Cart cart) {
        Optional<Integer> numberProducts = cartDetailsRepository.sumNumberProducts(cart.getId());
        Optional<Double> amount = cartDetailsRepository.sumAmount(cart.getId());

        if (numberProducts.isPresent() && amount.isPresent()) {
            cart.setNumberProducts(numberProducts.get());
            cart.setAmount(amount.get());
        } else {
            cart.setNumberProducts(0);
            cart.setAmount(0.0);
        }

        cartRepository.save(cart);
    }
}
