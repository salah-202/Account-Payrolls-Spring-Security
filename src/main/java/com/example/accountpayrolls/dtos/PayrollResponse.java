package com.example.accountpayrolls.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class PayrollResponse {
    private String name;
    private String lastName;
    private String period;
    private long salary;
}
