package com.example.accountpayrolls.exceptions;

import lombok.Data;

import java.util.Date;
@Data
public class ErrorResponse {
    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
