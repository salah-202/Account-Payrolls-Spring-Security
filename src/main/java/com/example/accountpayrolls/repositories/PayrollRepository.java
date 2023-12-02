package com.example.accountpayrolls.repositories;

import com.example.accountpayrolls.entities.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll,Integer> {
    Optional<List<Payroll>> findByEmployee(String employee);
    Optional<Payroll> findByPeriod(String period);
}
