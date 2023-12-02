package com.example.accountpayrolls.validations;

import com.example.accountpayrolls.exceptions.NotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
@Component
public class ValidatePassword {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ValidatePassword(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    public boolean isValidPassword(String password){
        if (!isPasswordLong(password)) {
            throw new NotValidException("The password length must be at least 12 chars!");
        }else if (isHackedPassword(password)) {
            throw new NotValidException("The password is in the hacker's database!");
        }
        return true;
    }
    public boolean isValidPassword(String oldPass,String newPass){
        if (!isPasswordLong(newPass)) {
            throw new NotValidException("The password length must be at least 12 chars!");
        }else if (isHackedPassword(oldPass)) {
            throw new NotValidException("The password is in the hacker's database!");
        }else if (isMatchedPassword(newPass,oldPass)) {
            throw new NotValidException("The passwords must be different!");
        }
        return true;
    }

    private boolean isPasswordLong(String password){
        return password.length() >= 12;
    }
    private boolean isHackedPassword(String password){
        //For testing purposes, here is the list of breached passwords
        ArrayList<String> hackersPasswords = new ArrayList<>(Arrays.asList("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"));

        for (String hackedPassword:hackersPasswords) {
            if(hackedPassword.equals(password)){
                return true;
            }
        }
        return false;
    }
    private boolean isMatchedPassword(String newPass,String oldPass){
        return passwordEncoder.matches(newPass,oldPass);
    }
}
