package com.example.accountpayrolls.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "payrolls")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "employee")
    @NotNull
    private String employee;
    @Column(name = "period")
    @NotNull
    @JsonFormat(pattern = "MM-YYYY")
    private String period;
    @Column(name = "salary")
    @NotNull
    private long salary;
    /*
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH,  CascadeType.REFRESH})
    @JoinColumn(name = "employee",referencedColumnName = "email")
    private AppUser appUser;
     */
}
