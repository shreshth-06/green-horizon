package com.greenhorizon.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhorizon.main.entity.Cart;
import com.greenhorizon.main.entity.CartItem;
import com.greenhorizon.main.entity.Order;
import com.greenhorizon.main.entity.OrderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	List<OrderItem> findByOrder(Order order);
}
