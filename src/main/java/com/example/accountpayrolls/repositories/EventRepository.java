package com.example.accountpayrolls.repositories;

import com.example.accountpayrolls.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Integer> {
}
