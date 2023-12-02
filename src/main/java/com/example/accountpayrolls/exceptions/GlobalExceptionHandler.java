package com.example.accountpayrolls.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidData(NotValidException ex, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(ex.getStatusCode().value());
        errorResponse.setError(ex.getStatusCode().getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse,ex.getStatusCode());
    }
    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedEmail(DuplicatedException ex, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(ex.getStatusCode().value());
        errorResponse.setError(ex.getStatusCode().getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse,ex.getStatusCode());
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundEmail(NotFoundException ex, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(ex.getStatusCode().value());
        errorResponse.setError(ex.getStatusCode().getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse,ex.getStatusCode());
    }
}
