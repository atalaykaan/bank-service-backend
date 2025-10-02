package com.atalaykaan.bankservicebackend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GeneralExceptionAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {

        return new ResponseEntity<Object>(
                List.of(ex.getMessage(), request.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) throws Exception {

        return new ResponseEntity<Object>(List.of(ex.getMessage(),
                request.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {

        return new ResponseEntity<Object>(List.of(ex.getMessage(),
                request.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> handleWalletNotFoundException(WalletNotFoundException ex, WebRequest request) throws Exception {

        return new ResponseEntity<Object>(List.of(ex.getMessage(),
                request.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException ex, WebRequest request) throws Exception {

        return new ResponseEntity<Object>(List.of(ex.getMessage(),
                request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletWithCurrencyAlreadyExistsException.class)
    public ResponseEntity<Object> handleWalletWithCurrencyAlreadyExistsException(WalletWithCurrencyAlreadyExistsException ex, WebRequest request) throws Exception {

        return new ResponseEntity<Object>(List.of(ex.getMessage(),
                request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithAccountExistsException.class)
    public ResponseEntity<Object> handleUserWithAccountExistsException(UserWithAccountExistsException ex, WebRequest request) throws Exception {

        return new ResponseEntity<Object>(List.of(ex.getMessage(),
                request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }

}
