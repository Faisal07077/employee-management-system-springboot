package com.seams.employee_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format"
    )
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;


    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Phone number must be 10 digits and start with 6-9"
    )
    @NotBlank(message = "Phone number is required")
    @Column(unique = true)
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;

    @Positive(message = "Salary must be greater than 0")
    @NotNull(message = "Salary is required")
    private Double salary;

    @NotNull(message = "Join date is required")
    private LocalDate joinDate;

    @NotBlank(message = "Status is required")
    private String status;
}
