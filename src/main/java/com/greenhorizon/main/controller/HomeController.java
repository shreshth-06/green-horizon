package com.greenhorizon.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenhorizon.main.entity.Category;
import com.greenhorizon.main.entity.Product;
import com.greenhorizon.main.repository.CategoryRepository;
import com.greenhorizon.main.repository.ProductRepository;
import com.greenhorizon.main.service.ProductService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Data
public class HomeController {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@GetMapping("/home")
	public String home(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) Long categoryId) 
	{

		// Fetch all categories
		List<Category> categories = categoryRepository.findAll();
		
		model.addAttribute("categories", categories);
		model.addAttribute("selectedCategory", categoryId);

		// Fetch filtered products
		List<Product> products;
		
		if (categoryId != null && categoryId != 0 && search != null && !search.isEmpty())
		{
			products = productRepository.findByCategoryIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(categoryId, search, search);
		} 
		else if (categoryId != null && categoryId != 0) 
		{
			products = productRepository.findByCategoryId(categoryId);  
		}
		else if (search != null && !search.isEmpty())  
		{
//			products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, categoryId);
		
			String categoryName = search;
			Long catId = categoryRepository.findIdByName(categoryName);
			
			products = productRepository.findByCategoryId(catId); 
		
			model.addAttribute("selectedCategory", catId);
		
		}
		else 
		{
			products = productRepository.findAll();
		}
		model.addAttribute("product", products);
		model.addAttribute("search", search);

		return "home";
	}
}



