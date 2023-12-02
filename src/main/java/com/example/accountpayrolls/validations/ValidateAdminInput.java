package com.example.accountpayrolls.validations;

import com.example.accountpayrolls.dtos.UpdateRoleRequest;
import com.example.accountpayrolls.entities.AppUser;
import com.example.accountpayrolls.entities.Role;
import com.example.accountpayrolls.exceptions.NotFoundException;
import com.example.accountpayrolls.exceptions.NotValidException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
@Component
public class ValidateAdminInput {
    public boolean isValidInput(AppUser appUser, Role role, UpdateRoleRequest roleRequest,Set<Role> adminOppositeRoles){
        if(roleRequest.getOperation().equals("GRANT")){
            validateRoleCombination(appUser.getRoles(),role,adminOppositeRoles);
            appUser.getRoles().add(role);
        } else if (roleRequest.getOperation().equals("REMOVE")) {
            validateRoleRemoval(appUser.getRoles(),role);
            appUser.getRoles().remove(role);
        }else {
            throw new NotFoundException("Not found operation");
        }
        return true;
    }

    public boolean validateRoleCombination(Set<Role> userRoles, Role role, Set<Role> adminOppositeRoles){
        if (!Collections.disjoint(userRoles,adminOppositeRoles) && role.getName().equals("ROLE_ADMIN")){
            throw new NotValidException("The user cannot combine administrative and business roles!");
        }
        return true;
    }
    public boolean validateRoleRemoval(Set<Role> userRoles,Role role){
        if(!userRoles.contains(role)){
            throw new NotValidException("The user does not have a role!");
        } else if (userRoles.size() == 1) {
            throw new NotValidException("The user must have at least one role!");
        } else if (role.getName().equals("ROLE_ADMIN")) {
            throw new NotValidException("Can't remove ADMINISTRATOR role!");
        }
        return true;
    }
}
