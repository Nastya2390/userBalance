package com.example.userBalance.controllers;

import com.example.userBalance.services.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final UserBalanceService userBalanceService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {
        return userBalanceService.getBalance(userId);
    }

    @PostMapping("/decrease")
    public ResponseEntity<?> decreaseBalance(@RequestParam(value = "userId") Long userId,
                                      @RequestParam(value = "amount") Double amount) {
        return userBalanceService.decreaseBalance(userId, amount);
    }

    @PostMapping("/increase")
    public ResponseEntity<?> increaseBalance(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "amount") Double amount) {
        return userBalanceService.increaseBalance(userId, amount);
    }

}
