package com.atalaykaan.bankservicebackend.exception;

public class UserWithAccountExistsException extends RuntimeException {

    public UserWithAccountExistsException(String message) {

        super(message);
    }
}
