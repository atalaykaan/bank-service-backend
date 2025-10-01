package com.atalaykaan.bankservicebackend.exception;

public class WalletWithCurrencyAlreadyExistsException extends RuntimeException {

    public WalletWithCurrencyAlreadyExistsException(String message) {

        super(message);
    }
}
