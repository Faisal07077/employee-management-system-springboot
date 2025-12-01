package com.seams.employee_system.controller;

import com.seams.employee_system.entity.Attendance;
import com.seams.employee_system.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    // AttendanceController.java

// existing punch-in & punch-out(attendanceId) stay if you want

    // 🔴 Punch out for today's record using employeeId
    @PostMapping("/punch-out-today/{employeeId}")
    public Attendance punchOutToday(@PathVariable Long employeeId) {
        return service.punchOutForToday(employeeId);
    }

    // 🔴 Attendance history for employee
    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getAttendanceForEmployee(@PathVariable Long employeeId) {
        return service.getAttendanceForEmployee(employeeId);
    }

}
