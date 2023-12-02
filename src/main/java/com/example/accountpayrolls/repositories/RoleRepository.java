package com.example.accountpayrolls.repositories;

import com.example.accountpayrolls.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String role);
    Boolean existsByName(String role);

}
