package com.example.accountpayrolls.services;

import com.example.accountpayrolls.dtos.PayrollResponse;
import com.example.accountpayrolls.dtos.StatusResponse;
import com.example.accountpayrolls.entities.Payroll;
import com.example.accountpayrolls.exceptions.NotFoundException;
import com.example.accountpayrolls.repositories.PayrollRepository;
import com.example.accountpayrolls.validations.ValidateAccountantInput;
import com.example.accountpayrolls.validations.ValidateEmail;
import com.example.accountpayrolls.validations.ValidateUserInput;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountantPaymentService {
    private final PayrollRepository payrollRepository;
    private final ValidateEmail validateEmail;
    private final ValidateAccountantInput validateAccountantInput;
    public AccountantPaymentService(PayrollRepository payrollRepository, ValidateEmail validateEmail,
                                    ValidateAccountantInput validateAccountantInput){
        this.payrollRepository = payrollRepository;
        this.validateEmail = validateEmail;
        this.validateAccountantInput = validateAccountantInput;
    }
    public StatusResponse updatePayroll(Payroll payroll){
        Payroll savedPayroll = payrollRepository.findByPeriod(payroll.getPeriod()).orElseThrow(
                () -> new NotFoundException("there's no payroll in " + payroll.getPeriod()+" period !"));
        savedPayroll.setSalary(payroll.getSalary());
        payrollRepository.save(payroll);

        return new StatusResponse("Updated successfully!");
    }
    @Transactional
    public StatusResponse addPayrolls(List<Payroll> payrolls){
        for (Payroll payroll:payrolls) {
            if(validateEmail.isEmailFound(payroll.getEmployee())
                    && validateAccountantInput.isInputValid(payroll)){
                payrollRepository.save(payroll);
            }
        }
        return new StatusResponse("Added successfully!");
    }
}
