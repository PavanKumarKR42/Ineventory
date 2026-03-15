package com.example.Inventory.service;

import com.example.Inventory.dto.CreateEmployeeRequest;
import com.example.Inventory.model.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findByEmail(String email);

    Account save(Account account);

    Account createEmployee(CreateEmployeeRequest request, String adminEmail);

}