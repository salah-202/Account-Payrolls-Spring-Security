package com.example.accountpayrolls.services;

import com.example.accountpayrolls.controllers.EmployeePaymentController;
import com.example.accountpayrolls.dtos.PayrollResponse;
import com.example.accountpayrolls.entities.AppUser;
import com.example.accountpayrolls.entities.Payroll;
import com.example.accountpayrolls.exceptions.NotFoundException;
import com.example.accountpayrolls.repositories.PayrollRepository;
import com.example.accountpayrolls.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class EmployeePaymentService {
    private final PayrollRepository payrollRepository;
    private final UserRepository userRepository;
    @Autowired
    public EmployeePaymentService(PayrollRepository payrollRepository,
                                  UserRepository userRepository){
        this.payrollRepository = payrollRepository;
        this.userRepository = userRepository;
    }
    public List<PayrollResponse> getPayrolls(String email){
        List<Payroll> payrolls = payrollRepository.findByEmployee(email).orElseThrow(
                ()-> new NotFoundException("there's no payrolls for now, check later"));

        AppUser appUser = userRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("there's no email for "+email));
        String fName = appUser.getFirstName();
        String lName = appUser.getLastName();

        return payrolls.stream().map(
                payroll -> new PayrollResponse(fName, lName, payroll.getPeriod(),payroll.getSalary()))
                .toList();
    }
    public List<PayrollResponse> getPayrolls(String email,String period){
        Payroll payroll = payrollRepository.findByPeriod(period).orElseThrow(
                ()-> new NotFoundException("there's no payrolls for "+period+" date !"));

        AppUser appUser = userRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("there's no email for "+email));
        String fName = appUser.getFirstName();
        String lName = appUser.getLastName();

        return new ArrayList<>(List.of(new PayrollResponse(fName, lName, period, payroll.getSalary())));
    }
}
