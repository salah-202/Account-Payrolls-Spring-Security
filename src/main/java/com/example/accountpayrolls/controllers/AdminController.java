package com.example.accountpayrolls.controllers;

import com.example.accountpayrolls.dtos.ManageAccessRequest;
import com.example.accountpayrolls.dtos.StatusResponse;
import com.example.accountpayrolls.dtos.UpdateRoleRequest;
import com.example.accountpayrolls.entities.UserDto;
import com.example.accountpayrolls.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/admin/user")
public class AdminController {
    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }
    @PutMapping("/role")
    public ResponseEntity<UserDto> updateUserRoles(@RequestBody UpdateRoleRequest roleRequest,
                                                   @AuthenticationPrincipal UserDetails userDetails){
        UserDto userDto = adminService.updateUserRole(roleRequest,userDetails.getUsername());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<StatusResponse> deleteUser(@PathVariable String email,
                                                     @AuthenticationPrincipal UserDetails userDetails){
        StatusResponse statusResponse = adminService.deleteUser(email,userDetails.getUsername());
        return new ResponseEntity<>(statusResponse,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> userDtos = adminService.getUsers();
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }
    @PutMapping("/access")
    public ResponseEntity<StatusResponse> manageAccess(@RequestBody ManageAccessRequest accessRequest){
        StatusResponse statusResponse = adminService.manageAccess(accessRequest);
        return new ResponseEntity<>(statusResponse,HttpStatus.OK);
    }
}
