package com.example.accountpayrolls.validations;

import com.example.accountpayrolls.entities.Payroll;
import com.example.accountpayrolls.exceptions.DuplicatedException;
import com.example.accountpayrolls.exceptions.NotValidException;
import com.example.accountpayrolls.repositories.PayrollRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateAccountantInput {
    private final PayrollRepository payrollRepository;
    public ValidateAccountantInput(PayrollRepository payrollRepository){
        this.payrollRepository = payrollRepository;
    }

    public boolean isInputValid(Payroll payroll){
        if (isPayrollInputNull(payroll)) {
            throw new NotValidException("Enter all the fields");
        } else if (payroll.getSalary() < 0) {
            throw new NotValidException("Salary should be greater than zero");
        }else if (!isDateValid(payroll.getPeriod())) {
            throw new NotValidException("Enter Correct Date");
        }else if (!isDateNotFound(payroll.getPeriod())) {
            throw new DuplicatedException("this period is already saved !");
        }
        return true;
    }

    public boolean isPayrollInputNull(Payroll payroll){
        return payroll.getPeriod() == null;
    }
    public boolean isDateValid(String date){
        String[] parts = date.split("-");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        return (month >0 && month < 13) &&  (year >2010 && year < 2024);
    }

    private boolean isDateNotFound(String date){
        return payrollRepository.findByPeriod(date).isEmpty();
    }

}
