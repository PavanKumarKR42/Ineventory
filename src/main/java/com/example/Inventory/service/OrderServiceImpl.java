package com.example.Inventory.service;

import com.example.Inventory.dto.CreateOrderRequest;
import com.example.Inventory.dto.OrderItemRequest;
import com.example.Inventory.model.*;
import com.example.Inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final ActivityLogRepository activityLogRepository;

    @Override
    public Order createOrder(CreateOrderRequest request, String customerEmail) {

        Account customer = accountRepository
                .findByEmail(customerEmail)
                .orElseThrow();

        Order order = Order.builder()
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .build();

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository
                    .findByName(itemRequest.getProductName())
                    .orElseThrow();

            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock");
            }

            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(price)
                    .build();

            orderItemRepository.save(item);

            total = total.add(price.multiply(
                    BigDecimal.valueOf(itemRequest.getQuantity())
            ));
        }

        order.setTotalAmount(total);
        orderRepository.save(order);

        ActivityLog log = ActivityLog.builder()
                .action("CUSTOMER_PURCHASE")
                .performedBy(customerEmail)
                .createdAt(LocalDateTime.now())
                .build();

        activityLogRepository.save(log);

        return order;
    }
}