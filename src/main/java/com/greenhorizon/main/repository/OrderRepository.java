package com.greenhorizon.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhorizon.main.entity.Order;
import com.greenhorizon.main.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ Get orders of a specific user
    List<Order> findByUser(User user);

    // ✅ Optional: Filter by status (for admin dashboard)
    List<Order> findByStatus(String status);
    
    
}
