package com.greenhorizon.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhorizon.main.entity.Category;
import com.greenhorizon.main.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> 
{
    List<Product> findByCategory(Category category);
 
    List<Product> findByCategoryId(Long categoryId);
    
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, Long categoryId);

    List<Product> findByCategoryIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Long categoryId, String name, String description);
}




