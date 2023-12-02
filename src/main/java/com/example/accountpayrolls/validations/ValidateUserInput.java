package com.example.accountpayrolls.validations;

import com.example.accountpayrolls.entities.AppUser;
import com.example.accountpayrolls.exceptions.NotValidException;
import org.springframework.stereotype.Component;
@Component
public class ValidateUserInput {
    public boolean isInputValid(AppUser appUser){
        if (isAppUserInputNull(appUser)) {
            throw new NotValidException("Enter all the fields");
        }
        return true;
    }
    public boolean isAppUserInputNull(AppUser appUser){
        return appUser.getFirstName() == null || appUser.getLastName() == null;
    }
}
