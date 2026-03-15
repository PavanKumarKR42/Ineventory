package com.example.Inventory.controller;

import com.example.Inventory.dto.UserSignupRequest;
import com.example.Inventory.model.Account;
import com.example.Inventory.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

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
}