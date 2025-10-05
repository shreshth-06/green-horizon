package com.greenhorizon.main.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.greenhorizon.main.entity.Cart;
import com.greenhorizon.main.entity.CartItem;
import com.greenhorizon.main.entity.Order;
import com.greenhorizon.main.entity.OrderItem;
import com.greenhorizon.main.entity.Product;
import com.greenhorizon.main.entity.User;
import com.greenhorizon.main.repository.CartItemRepository;
import com.greenhorizon.main.repository.CartRepository;
import com.greenhorizon.main.repository.OrderItemRepository;
import com.greenhorizon.main.repository.OrderRepository;
import com.greenhorizon.main.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Order placeOrder(User user) 
    {
        Cart cart = cartService.getCartByUser(user);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setItems(new ArrayList<>());
        double total = 0;

        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setTotalPrice(ci.getTotalPrice());

            order.getItems().add(oi);
            total += ci.getTotalPrice();
        }
        
        System.out.println("TOTAL VALUE :: "+total);

        order.setTotalPrice(total);
        
        System.out.println("TOTAL VALUE :: SET DONE "+total);
        
        Order savedOrder = orderRepository.save(order);
        
        System.out.println("AFTER orderRepository.save(order)");

        // Clear cart
        cartItemRepository.deleteAll(cartItems);
        cart.setTotalPrice(0);
        cartService.updateCartTotal(cart);

        return savedOrder;
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }
    
    
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

 
    
    
    
}
