package com.atalaykaan.bankservicebackend.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {

        super(message);
    }
}
