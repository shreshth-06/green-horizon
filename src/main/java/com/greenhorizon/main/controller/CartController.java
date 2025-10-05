package com.greenhorizon.main.controller;

import com.greenhorizon.main.entity.Cart;
import com.greenhorizon.main.entity.CartItem;
import com.greenhorizon.main.entity.User;
import com.greenhorizon.main.service.CartService;
import com.greenhorizon.main.service.UserService;
import com.greenhorizon.main.repository.CartItemRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;

    public CartController(CartService cartService, UserService userService,
                          CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }

    private User getAuthenticatedUser() {
        return userService.getAllUsers().get(0); // Replace with Spring Security
    }

    @GetMapping
    public String viewCart(Model model) {
        User user = getAuthenticatedUser();
        Cart cart = cartService.getCartByUser(user);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        model.addAttribute("cart", cart);
        model.addAttribute("items", items);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity) {
        cartService.addToCart(getAuthenticatedUser(), productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("itemId") Long itemId,
                                 @RequestParam("quantity") int quantity) {
        cartService.updateQuantity(getAuthenticatedUser(), itemId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Long itemId) {
        cartService.removeFromCart(getAuthenticatedUser(), itemId);
        return "redirect:/cart";
    }
}
