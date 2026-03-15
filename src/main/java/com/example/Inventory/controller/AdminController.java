package com.example.Inventory.controller;

import com.example.Inventory.dto.CreateEmployeeRequest;
import com.example.Inventory.model.Account;
import com.example.Inventory.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;

    @PostMapping("/create-employee")
    public Account createEmployee(@RequestBody CreateEmployeeRequest request) {

        return accountService.createEmployee(request, "admin@inventory.com");

    }
}