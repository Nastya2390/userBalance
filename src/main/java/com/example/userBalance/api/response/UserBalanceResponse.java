package com.example.userBalance.api.response;

import lombok.Data;

@Data
public class UserBalanceResponse {
    private boolean result;
    private String errorMessage;

    public UserBalanceResponse(boolean result) {
        this.result = result;
        errorMessage = "";
    }

    public UserBalanceResponse(boolean result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

}
