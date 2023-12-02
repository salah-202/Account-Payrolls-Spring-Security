package com.example.accountpayrolls.services;

import com.example.accountpayrolls.dtos.ManageAccessRequest;
import com.example.accountpayrolls.dtos.StatusResponse;
import com.example.accountpayrolls.dtos.UpdateRoleRequest;
import com.example.accountpayrolls.entities.*;
import com.example.accountpayrolls.exceptions.NotFoundException;
import com.example.accountpayrolls.exceptions.NotValidException;
import com.example.accountpayrolls.repositories.EventRepository;
import com.example.accountpayrolls.repositories.RoleRepository;
import com.example.accountpayrolls.repositories.UserRepository;
import com.example.accountpayrolls.validations.ValidateAdminInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EventRepository eventRepository;
    private final ValidateAdminInput validateAdminInput;
    @Autowired
    public AdminService (UserRepository userRepository,RoleRepository roleRepository,
                         ValidateAdminInput validateAdminInput,EventRepository eventRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validateAdminInput = validateAdminInput;
        this.eventRepository = eventRepository;
    }
    public UserDto updateUserRole(UpdateRoleRequest roleRequest,String userName){
        AppUser appUser = userRepository.findByEmail(roleRequest.getUser()).orElseThrow(
                ()->new NotFoundException("User not found!"));
        Role role = roleRepository.findByName("ROLE_"+roleRequest.getRole()).orElseThrow(
                ()->new NotFoundException("Role not found!"));
        Set<Role> adminOppositeRoles = new HashSet<>(Arrays.asList(
                getRoleByName("ROLE_USER"),getRoleByName("ROLE_ACCOUNTANT")
                ,getRoleByName("ROLE_AUDITOR")));

        validateAdminInput.isValidInput(appUser,role,roleRequest,adminOppositeRoles);
        eventRepository.save(new Event(roleRequest.getOperation()+"_ROLE",userName,appUser.getEmail(),"api/admin/user/role"));
        return UserMapper.INSTANCE.appUserToUserDto(userRepository.save(appUser));
    }
    public StatusResponse deleteUser(String email,String userName){
        AppUser appUser = userRepository.findByEmail(email).orElseThrow(
                ()->new NotFoundException("User not found!"));
        if (appUser.getRoles()
                .stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))){
            throw new NotValidException("Can't remove ADMINISTRATOR role!");
        }
        userRepository.delete(appUser);
        eventRepository.save(new Event("DELETE_USER",userName,email,"api/admin/user/"+email));
        return new StatusResponse("Deleted successfully!");
    }
    public List<UserDto> getUsers(){
        List<AppUser> userList = userRepository.findAll(Sort.by("id").ascending());
        return userList.stream()
                .map(UserMapper.INSTANCE::appUserToUserDto)
                .toList();
    }
    public StatusResponse manageAccess(ManageAccessRequest accessRequest){
        AppUser appUser = userRepository.findByEmail(accessRequest.getUser()).orElseThrow(
                ()->new NotFoundException("Not Found user!"));
        if (appUser.getRoles()
                .stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))){
            throw new NotValidException("Can't Lock the ADMINISTRATOR !");
        }
        StatusResponse statusResponse;
        if(accessRequest.getOperation().equals("LOCK")){
            appUser.setNonLocked(false);
            statusResponse = new StatusResponse("Email "+accessRequest.getUser()+" is Locked successfully");
        }else if(accessRequest.getOperation().equals("UNLOCK")) {
            appUser.setNonLocked(true);
            appUser.setFailedAttempt(0);
            statusResponse = new StatusResponse("Email "+accessRequest.getUser()+" is Unlocked successfully");
        }else {
            throw new NotFoundException("Not found Operation !");
        }
        userRepository.save(appUser);
        return statusResponse;
    }
    public Role getRoleByName(String roleName){
        return roleRepository.findByName(roleName).orElseThrow(
                ()->new NotFoundException("Not found role"));
    }
}
