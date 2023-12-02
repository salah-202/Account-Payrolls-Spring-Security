package com.example.accountpayrolls.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ManageAccessRequest {
    private String user;
    private String operation;
}
