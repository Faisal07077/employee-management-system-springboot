package com.seams.employee_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime punchIn;

    private LocalTime punchOut;

    private Double workingHours;

    private String status; // PRESENT / ABSENT

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
