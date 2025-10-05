package com.greenhorizon.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhorizon.main.entity.Cart;
import com.greenhorizon.main.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
