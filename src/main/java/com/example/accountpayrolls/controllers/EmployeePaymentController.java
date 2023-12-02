package com.example.accountpayrolls.controllers;

import com.example.accountpayrolls.dtos.PayrollResponse;
import com.example.accountpayrolls.entities.Payroll;
import com.example.accountpayrolls.entities.UserDto;
import com.example.accountpayrolls.services.AuthenticationService;
import com.example.accountpayrolls.services.EmployeePaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/empl")
public class EmployeePaymentController {
    private final EmployeePaymentService employeePaymentService;
    public EmployeePaymentController(EmployeePaymentService employeePaymentService){
        this.employeePaymentService = employeePaymentService;
    }
    @GetMapping("/payment")
    public ResponseEntity<List<PayrollResponse>> getPayments(@AuthenticationPrincipal UserDetails userDetails,
                                                             @RequestParam(required = false) String period){
        String email = userDetails.getUsername();
        if(period == null){
            List<PayrollResponse> payrolls = employeePaymentService.getPayrolls(email);
            return new ResponseEntity<>(payrolls, HttpStatus.OK);
        }
        List<PayrollResponse> payroll = employeePaymentService.getPayrolls(email,period);
        return new ResponseEntity<>(payroll, HttpStatus.OK);
    }
}
