package com.example.accountpayrolls.repositories;

import com.example.accountpayrolls.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Integer> {
     Optional<AppUser> findByEmail(String email);
     Optional<AppUser> deleteByEmail(String email);
}
