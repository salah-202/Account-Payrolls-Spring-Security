package com.example.accountpayrolls.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    @NotNull
    private final Date date = new Date();
    @Column(name = "action")
    @NotNull
    private String action;
    @Column(name = "subject")
    @NotNull
    private String subject;
    @Column(name = "object")
    @NotNull
    private String object;
    @Column(name = "path")
    @NotNull
    private String path;
    public Event(String action,String subject,String object,String path){
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }
}
