package com.example.accountpayrolls.controllers;

import com.example.accountpayrolls.entities.Event;
import com.example.accountpayrolls.services.AuditorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/security/events")
public class AuditorController {

    private final AuditorService auditorService;
    public AuditorController(AuditorService auditorService){
        this.auditorService = auditorService;
    }
    @GetMapping
    public ResponseEntity<List<Event>> getEvents(){
        return new ResponseEntity<>(auditorService.getEvents(), HttpStatus.OK);
    }
}
