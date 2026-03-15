package com.example.Inventory.service;

import com.example.Inventory.dto.AddStockRequest;
import com.example.Inventory.model.ActivityLog;
import com.example.Inventory.model.Product;
import com.example.Inventory.repository.ActivityLogRepository;
import com.example.Inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ActivityLogRepository activityLogRepository;

    @Override
    public Product addStock(AddStockRequest request, String employeeEmail) {

        Product product = productRepository
                .findByName(request.getProductName())
                .orElseThrow(() -> new RuntimeException("Invalid product"));

        int newQuantity = product.getQuantity() + request.getQuantity();

        if (newQuantity > 10) {
            throw new RuntimeException("Maximum quantity limit is 10");
        }

        product.setQuantity(newQuantity);

        Product saved = productRepository.save(product);

        ActivityLog log = ActivityLog.builder()
                .action("Employee added stock for " + product.getName() +
                        " quantity " + request.getQuantity())
                .performedBy(employeeEmail)
                .createdAt(LocalDateTime.now())
                .build();

        activityLogRepository.save(log);

        return saved;
    }
}