package com.example.userBalance.services;

import com.example.userBalance.model.UserBalance;
import com.example.userBalance.repository.UserBalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBalanceServiceImplTest {
    private static final long ID = 1L;

    @Mock
    private UserBalanceRepository userBalanceRepository;

    @InjectMocks
    private UserBalanceServiceImpl userBalanceService;

    @Test
    void getBalanceTest() {
        UserBalance userBalance = mock(UserBalance.class);
        when(userBalanceRepository.getUserBalanceByUserId(ID)).thenReturn(Optional.of(userBalance));
        when(userBalance.getBalance()).thenReturn(500.0);

        ResponseEntity response = userBalanceService.getBalance(ID);

        assertNotNull(response);
        assertEquals(500.0, response.getBody());
        verify(userBalanceRepository).getUserBalanceByUserId(ID);
    }

    @Test
    void getBalanceTest_cannotFindUser() {
        ResponseEntity response = userBalanceService.getBalance(2L);

        assertNotNull(response);
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Cannot find user with userId = " + 2L));
        verify(userBalanceRepository).getUserBalanceByUserId(2L);
    }

    @Test
    void decreaseBalanceTest() {
        UserBalance userBalance = mock(UserBalance.class);
        when(userBalanceRepository.getUserBalanceByUserId(ID)).thenReturn(Optional.of(userBalance));
        when(userBalance.getBalance()).thenReturn(500.0);

        ResponseEntity response = userBalanceService.decreaseBalance(ID, 100.0);

        assertNotNull(response);
        assertEquals(400.0, response.getBody());
        verify(userBalanceRepository).getUserBalanceByUserId(ID);
    }

    @Test
    void decreaseBalance_insufficientFunds() {
        UserBalance userBalance = mock(UserBalance.class);
        when(userBalanceRepository.getUserBalanceByUserId(ID)).thenReturn(Optional.of(userBalance));
        when(userBalance.getBalance()).thenReturn(500.0);

        ResponseEntity response = userBalanceService.decreaseBalance(ID, 1000.0);

        assertNotNull(response);
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Insufficient funds for user with userId = " + ID));
        verify(userBalanceRepository).getUserBalanceByUserId(ID);
    }

    @Test
    void decreaseBalance_cannotFindUser() {
        ResponseEntity response = userBalanceService.decreaseBalance(2L, 10.0);

        assertNotNull(response);
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Cannot find user with userId = " + 2L));
        verify(userBalanceRepository).getUserBalanceByUserId(2L);
    }

    @Test
    void increaseBalanceTest() {
        UserBalance userBalance = mock(UserBalance.class);
        when(userBalanceRepository.getUserBalanceByUserId(ID)).thenReturn(Optional.of(userBalance));
        when(userBalance.getBalance()).thenReturn(500.0);

        ResponseEntity response = userBalanceService.increaseBalance(ID, 100.0);

        assertNotNull(response);
        assertEquals(600.0, response.getBody());
        verify(userBalanceRepository).getUserBalanceByUserId(ID);
    }

    @Test
    void increaseBalance_cannotFindUser() {
        ResponseEntity response = userBalanceService.increaseBalance(2L, 10.0);

        assertNotNull(response);
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Cannot find user with userId = " + 2L));
        verify(userBalanceRepository).getUserBalanceByUserId(2L);
    }

}