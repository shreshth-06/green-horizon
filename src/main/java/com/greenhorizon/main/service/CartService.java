package com.greenhorizon.main.service;

import com.greenhorizon.main.entity.Cart;
import com.greenhorizon.main.entity.CartItem;
import com.greenhorizon.main.entity.Product;
import com.greenhorizon.main.entity.User;
import com.greenhorizon.main.repository.CartItemRepository;
import com.greenhorizon.main.repository.CartRepository;
import com.greenhorizon.main.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setTotalPrice(0);
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public void addToCart(User user, Long productId, int quantity) {
        Cart cart = getCartByUser(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if item exists in DB
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCart(cart).stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setTotalPrice(existingItem.getQuantity() * product.getPrice());
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setTotalPrice(quantity * product.getPrice());
            cartItemRepository.save(newItem);
        }

        updateCartTotal(cart);
    }

    @Transactional
    public void removeFromCart(User user, Long cartItemId) {
        Cart cart = getCartByUser(user);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItemRepository.delete(item);
        updateCartTotal(cart);
    }

    @Transactional
    public void updateQuantity(User user, Long cartItemId, int quantity) {
        Cart cart = getCartByUser(user);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        item.setTotalPrice(quantity * item.getProduct().getPrice());
        cartItemRepository.save(item);
        updateCartTotal(cart);
    }

    public void updateCartTotal(Cart cart) {
        double total = cartItemRepository.findByCart(cart).stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }
}
