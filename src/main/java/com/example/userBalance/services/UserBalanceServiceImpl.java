package com.example.userBalance.services;

import com.example.userBalance.api.response.UserBalanceResponse;
import com.example.userBalance.model.UserBalance;
import com.example.userBalance.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final UserBalanceRepository balanceRepository;
    private final Map<Long, ReentrantLock> userLocks = new HashMap<>();

    @Override
    public ResponseEntity<?> getBalance(Long userId) {
        Optional<UserBalance> balanceOpt = balanceRepository.getUserBalanceByUserId(userId);
        if (balanceOpt.isEmpty())
            return new ResponseEntity<>(new UserBalanceResponse(false, "Cannot find user with userId = " + userId), HttpStatus.NOT_FOUND);
        UserBalance balance = balanceOpt.get();
        return new ResponseEntity<>(balance.getBalance(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> decreaseBalance(Long userId, Double amount) {
        ReentrantLock userLock = getUserLock(userId);
        userLock.lock();
        try {
            Optional<UserBalance> balanceOpt = balanceRepository.getUserBalanceByUserId(userId);
            if (balanceOpt.isEmpty())
                return new ResponseEntity<>(new UserBalanceResponse(false, "Cannot find user with userId = " + userId), HttpStatus.NOT_FOUND);
            UserBalance balance = balanceOpt.get();
            if (balance.getBalance() < amount)
                return new ResponseEntity<>(new UserBalanceResponse(false, "Insufficient funds for user with userId = " + userId), HttpStatus.BAD_REQUEST);
            Double decreasedBalance = balance.getBalance() - amount;
            balance.setBalance(decreasedBalance);
            balanceRepository.save(balance);
            return new ResponseEntity<>(decreasedBalance, HttpStatus.OK);
        } finally {
            userLock.unlock();
        }
    }

    @Override
    public ResponseEntity<?> increaseBalance(Long userId, Double amount) {
        ReentrantLock userLock = getUserLock(userId);
        userLock.lock();
        try {
            Optional<UserBalance> balanceOpt = balanceRepository.getUserBalanceByUserId(userId);
            if (balanceOpt.isEmpty())
                return new ResponseEntity<>(new UserBalanceResponse(false, "Cannot find user with userId = " + userId), HttpStatus.NOT_FOUND);
            UserBalance balance = balanceOpt.get();
            Double increasedBalance = balance.getBalance() + amount;
            balance.setBalance(increasedBalance);
            balanceRepository.save(balance);
            return new ResponseEntity<>(increasedBalance, HttpStatus.OK);
        } finally {
            userLock.unlock();
        }
    }

    private ReentrantLock getUserLock(Long userId) {
        userLocks.putIfAbsent(userId, new ReentrantLock());
        return userLocks.get(userId);
    }

}
