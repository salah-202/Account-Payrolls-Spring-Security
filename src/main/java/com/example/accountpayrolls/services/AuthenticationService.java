package com.example.accountpayrolls.services;

import com.example.accountpayrolls.dtos.UpdatePasswordResponse;
import com.example.accountpayrolls.entities.*;
import com.example.accountpayrolls.exceptions.*;
import com.example.accountpayrolls.repositories.EventRepository;
import com.example.accountpayrolls.repositories.RoleRepository;
import com.example.accountpayrolls.repositories.UserRepository;
import com.example.accountpayrolls.validations.ValidateEmail;
import com.example.accountpayrolls.validations.ValidatePassword;
import com.example.accountpayrolls.validations.ValidateUserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidateEmail validateEmail;
    private final ValidatePassword validatePassword;
    private final ValidateUserInput validateUserInput;
    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                 EventRepository eventRepository, PasswordEncoder passwordEncoder,
                                  ValidateEmail validateEmail, ValidatePassword validatePassword,
                                 ValidateUserInput validateUserInput){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
        this.validateEmail = validateEmail;
        this.validatePassword = validatePassword;
        this.validateUserInput = validateUserInput;
    }

    public UserDto addUser(AppUser appUser){
        validateEmail.isValidEmail(appUser.getEmail());
        validatePassword.isValidPassword(appUser.getPassword());
        validateUserInput.isInputValid(appUser);

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(()->new NotFoundException("Not found role"));
        appUser.setRoles(new HashSet<>(Collections.singleton(userRole)));

        if(userRepository.count() == 0){
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(()->new NotFoundException("Not found role"));
            appUser.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        eventRepository.save(new Event("CREATE_USER","Anonymous",appUser.getEmail(),"/api/auth/signup"));
        return UserMapper.INSTANCE.appUserToUserDto(userRepository.save(appUser));
    }
    public UserDto getUser(String email){
        AppUser appUser = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Email is not found"));
        return UserMapper.INSTANCE.appUserToUserDto(appUser);
    }
    public UpdatePasswordResponse updatePassword(String newPass,String oldPass,String email){
        validatePassword.isValidPassword(oldPass,newPass);
        AppUser appUser = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Email is not found"));
        appUser.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(appUser);
        eventRepository.save(new Event("CHANGE_PASSWORD",email,email,"/api/auth/changepass"));
        return new UpdatePasswordResponse(email,"The password has been updated successfully");
    }
    /*
    public StatusResponse login(LoginRequest loginRequest){
        AppUser appUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                ()-> new NotFoundException("Email is not found"));
        StatusResponse statusResponse;
        if (passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            statusResponse = new StatusResponse("Login successfully");
        }else if(appUser.getFailedAttempt() == 4){
            if(appUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))){
                throw new NotValidException("Can't lock the ADMINISTRATOR!");
            }
            appUser.setFailedAttempt(appUser.getFailedAttempt() + 1);
            appUser.setNonLocked(false);
            statusResponse = new StatusResponse("Email is Locked");
        }else {
            appUser.setFailedAttempt(appUser.getFailedAttempt() + 1);
            statusResponse = new StatusResponse("login fail ,try again");
        }
        return statusResponse;
    }
     */
}
