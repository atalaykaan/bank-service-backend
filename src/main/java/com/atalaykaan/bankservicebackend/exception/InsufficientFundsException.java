package com.atalaykaan.bankservicebackend.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {

        super(message);
    }
}
