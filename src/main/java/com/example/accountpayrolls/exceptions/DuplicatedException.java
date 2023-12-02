package com.example.accountpayrolls.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatedException extends ApiBaseException{
    public DuplicatedException(String message){
        super(message);
    }
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
