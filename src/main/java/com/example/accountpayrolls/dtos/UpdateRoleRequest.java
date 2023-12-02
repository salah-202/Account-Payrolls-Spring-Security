package com.example.accountpayrolls.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateRoleRequest {
    private String user;
    private String role;
    private String operation;
}
