package com.greenhorizon.main.service;

import org.springframework.stereotype.Service;

import com.greenhorizon.main.entity.Category;
import com.greenhorizon.main.entity.Product;
import com.greenhorizon.main.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService 
{
	
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll(); 
	}

	public List<Product> getProductsByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow();
	}

	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
