package com.example.userBalance.repository;

import com.example.userBalance.model.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {

    Optional<UserBalance> getUserBalanceByUserId(Long userId);

}
