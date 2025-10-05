package com.greenhorizon.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.greenhorizon.main.entity.Cart;
import com.greenhorizon.main.entity.Order;
import com.greenhorizon.main.entity.User;
import com.greenhorizon.main.service.CartService;
import com.greenhorizon.main.service.OrderService;
import com.greenhorizon.main.service.UserService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    public OrderController(OrderService orderService, UserService userService, CartService cartService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
    }

    private User getAuthenticatedUser() {
        return userService.getAllUsers().get(0); // Replace with Spring Security
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model) {
        User user = getAuthenticatedUser();
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        return "checkout"; // Thymeleaf checkout page
    }  

   

    @GetMapping("/my")
    public String myOrders(Model model) {
        User user = getAuthenticatedUser();
        List<Order> orders = orderService.getUserOrders(user);
        model.addAttribute("orders", orders);
        return "orders"; // Thymeleaf orders page
    }
    
    
    // After placing an order, redirect to this URL to show order details
    @GetMapping("/success/{orderId}")
    public String orderSuccess(@PathVariable Long orderId, Model model) 
    {
        // Fetch order from DB
        Order order = orderService.getOrderById(orderId);
        
        if (order == null)
        {
            // handle invalid order ID, maybe redirect to /orders or show error
            return "redirect:/orders";
        }
        
        // Assuming createdAt is LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDate = order.getCreatedAt().format(formatter);
        
        model.addAttribute("order", order);
        model.addAttribute("formattedDate", formattedDate);

        return "order-confirmation"; // should match your Thymeleaf template name
    }

    
    
    @GetMapping("/")
    public String viewOrders(Model model) {
        User user = getAuthenticatedUser(); // implement this to get logged-in user
        List<Order> orders = orderService.getUserOrders(user);
        model.addAttribute("orders", orders);
        return "orders"; // Thymeleaf template orders.html
    }
    
    
    @PostMapping("/place")
    public String placeOrderRedirect() {
        User user = getAuthenticatedUser(); // your method to get logged-in user
        Order order = orderService.placeOrder(user);

        // Redirect to the success page with the new order ID
        return "redirect:/orders/success/" + order.getId();
    }
    
//    @PostMapping("/place")
//    public String placeOrder() {
//        User user = getAuthenticatedUser();
//        orderService.placeOrder(user);
//        return "redirect:/orders/my";
//    }
//    
//    

    
    
    
    
    
    
    
    
    
}
