package com.tinie.Services.exceptions;

import lombok.Getter;

public class UnauthorisedException extends RuntimeException{
    @Getter
    private final long phoneNumber;
    public UnauthorisedException(String message, long phoneNumber){
        super(message);
        this.phoneNumber = phoneNumber;
    }
}

