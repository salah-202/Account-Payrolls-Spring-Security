package com.example.accountpayrolls.controllers;

import com.example.accountpayrolls.dtos.PayrollResponse;
import com.example.accountpayrolls.dtos.StatusResponse;
import com.example.accountpayrolls.entities.Payroll;
import com.example.accountpayrolls.services.AccountantPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/acct")
public class AccountantPaymentController {
    private final AccountantPaymentService accountantPaymentService;
    public AccountantPaymentController(AccountantPaymentService accountantPaymentService){
        this.accountantPaymentService = accountantPaymentService;
    }
    @PostMapping("/payments")
    public ResponseEntity<StatusResponse> uploadPayrolls(@RequestBody List<Payroll> payrolls){
        StatusResponse statusResponse = accountantPaymentService.addPayrolls(payrolls);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }
    @PutMapping("/payments")
    public ResponseEntity<StatusResponse> updatePayroll(@RequestBody Payroll payroll){
        StatusResponse statusResponse = accountantPaymentService.updatePayroll(payroll);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }
}
