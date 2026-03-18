package com.example.Inventory.controller;

import com.example.Inventory.dto.UserSignupRequest;
import com.example.Inventory.model.Account;
import com.example.Inventory.model.Order;
import com.example.Inventory.service.AccountService;
import com.example.Inventory.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.Inventory.dto.CreateOrderRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;
    private final OrderService orderService; // ✅ NEW
    private final PasswordEncoder passwordEncoder;

    // ✅ SIGNUP
    @PostMapping("/signup")
    public Account signup(@RequestBody UserSignupRequest request) {

        Account account = Account.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("CUSTOMER")
                .createdAt(LocalDateTime.now())
                .build();

        return accountService.save(account);
    }

    // ✅ GET USER ORDERS
    @GetMapping("/orders")
    public List<Order> getMyOrders(@RequestHeader("user-email") String email) {
        return orderService.getOrdersByCustomer(email);
    }
    @PostMapping("/orders")
    public Order createOrder(@RequestBody CreateOrderRequest request,
                             @RequestHeader("user-email") String email) {

        return orderService.createOrder(request, email);
    }
    // ✅ RETURN ITEM
    @PostMapping("/return/{orderItemId}")
    public String returnItem(@PathVariable Long orderItemId,
                             @RequestHeader("user-email") String email) {

        orderService.returnItem(orderItemId, email);
        return "Item returned successfully";
    }
}