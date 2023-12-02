package com.example.accountpayrolls.controllers;

import com.example.accountpayrolls.dtos.LoginRequest;
import com.example.accountpayrolls.dtos.StatusResponse;
import com.example.accountpayrolls.dtos.UpdatePasswordResponse;
import com.example.accountpayrolls.entities.AppUser;
import com.example.accountpayrolls.entities.UserDto;
import com.example.accountpayrolls.entities.UserMapper;
import com.example.accountpayrolls.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody AppUser appUser){
        UserDto userDto = authenticationService.addUser(appUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/changepass")
    public ResponseEntity<UpdatePasswordResponse> changePassword(@RequestBody String newPassword, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        String oldPassword = userDetails.getPassword();

        UpdatePasswordResponse updateUserPass = authenticationService.updatePassword(newPassword,oldPassword,email);
        return new ResponseEntity<>(updateUserPass, HttpStatus.OK);
    }
    /*
    @PostMapping("/login")
    public ResponseEntity<StatusResponse> login(@RequestBody LoginRequest loginRequest){
       StatusResponse statusResponse = authenticationService.login(loginRequest);
       return new ResponseEntity<>(statusResponse,HttpStatus.OK);
    }
     */
}
