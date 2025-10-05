package com.greenhorizon.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhorizon.main.entity.Cart;
import com.greenhorizon.main.entity.CartItem;

import java.util.List;
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}
