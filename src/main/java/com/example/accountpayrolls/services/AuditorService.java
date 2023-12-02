package com.example.accountpayrolls.services;

import com.example.accountpayrolls.entities.Event;
import com.example.accountpayrolls.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditorService {
    private final EventRepository eventRepository;
    @Autowired
    public AuditorService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }
    public List<Event> getEvents(){
        return eventRepository.findAll();
    }
}
