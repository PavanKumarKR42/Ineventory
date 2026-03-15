package com.example.Inventory.controller;

import com.example.Inventory.dto.AddStockRequest;
import com.example.Inventory.model.Product;
import com.example.Inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final ProductService productService;

    @PostMapping("/add-stock")
    public Product addStock(
            @RequestBody AddStockRequest request,
            Authentication authentication
    ) {

        String employeeEmail = authentication.getName();

        return productService.addStock(request, employeeEmail);
    }
}