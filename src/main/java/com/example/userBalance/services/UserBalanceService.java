package com.example.userBalance.services;

import org.springframework.http.ResponseEntity;

public interface UserBalanceService {
    ResponseEntity<?> getBalance(Long userId);

    ResponseEntity<?> decreaseBalance(Long userId, Double amount);

    ResponseEntity<?> increaseBalance(Long userId, Double amount);
}
