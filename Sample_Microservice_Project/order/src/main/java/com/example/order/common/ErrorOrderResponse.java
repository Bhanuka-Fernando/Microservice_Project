package com.example.order.common;

import lombok.Getter;

@Getter
public class ErrorOrderResponse implements orderResponse{
    private final String errorMessage;

    public ErrorOrderResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
