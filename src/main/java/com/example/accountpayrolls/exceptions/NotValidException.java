package com.example.accountpayrolls.exceptions;

import org.springframework.http.HttpStatus;

public class NotValidException extends ApiBaseException{
    public NotValidException(String message){
        super(message);
    }
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
