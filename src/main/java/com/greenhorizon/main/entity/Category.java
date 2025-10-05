package com.greenhorizon.main.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // Getters & Setters
}
