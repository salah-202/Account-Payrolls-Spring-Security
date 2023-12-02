package com.example.accountpayrolls.validations;

import com.example.accountpayrolls.entities.AppUser;
import com.example.accountpayrolls.exceptions.DuplicatedException;
import com.example.accountpayrolls.exceptions.NotFoundException;
import com.example.accountpayrolls.exceptions.NotValidException;
import com.example.accountpayrolls.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateEmail {
    private final UserRepository userRepository;

    @Autowired
    public ValidateEmail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidEmail(String email) {
        if (!isAcmeEmail(email)) {
            throw new NotValidException("Email should end with (@acme.com)");
        } else if (!isEmailNotFound(email)) {
            throw new DuplicatedException("Email is already register ,try to login");
        }
        return true;
    }

    private boolean isAcmeEmail(String email) {
        return email != null && email.endsWith("@acme.com");
    }

    private boolean isEmailNotFound(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean isEmailFound(String employee) {
        AppUser appUser = userRepository.findByEmail(employee).orElseThrow(() -> new NotFoundException("there's no employee for " + employee));
        return appUser != null;
    }

}
