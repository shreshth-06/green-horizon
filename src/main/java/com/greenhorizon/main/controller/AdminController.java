package com.greenhorizon.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.greenhorizon.main.entity.Order;
import com.greenhorizon.main.entity.Product;
import com.greenhorizon.main.service.OrderService;
import com.greenhorizon.main.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;

    public AdminController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/products")
    public String manageProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin-products";
    }

//    @GetMapping("/orders")
//    public String manageOrders(Model model) {
//        List<Order> orders = orderService.getAllOrders(); // Add getAllOrders in OrderService
//        model.addAttribute("orders", orders);
//        return "admin-orders";
//    }

//    @PostMapping("/orders/update/{id}")
//    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
//        orderService.updateOrderStatus(id, status);
//        return "redirect:/admin/orders";
//    }
}
