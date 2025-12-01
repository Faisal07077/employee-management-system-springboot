package com.seams.employee_system.service;

import com.seams.employee_system.entity.Attendance;
import com.seams.employee_system.entity.Employee;
import com.seams.employee_system.exception.ResourceNotFoundException;
import com.seams.employee_system.repository.AttendanceRepository;
import com.seams.employee_system.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    // ✅ PUNCH IN
    @Override
    public Attendance punchIn(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LocalDate today = LocalDate.now();

        // Prevent double punch-in
        attendanceRepository.findByEmployeeIdAndDate(employeeId, today)
                .ifPresent(a -> {
                    throw new RuntimeException("Already punched in today");
                });

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setPunchIn(LocalTime.now());
        attendance.setStatus("PRESENT");

        return attendanceRepository.save(attendance);
    }

    // ✅ PUNCH OUT
    @Override
    public Attendance punchOut(Long attendanceId) {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        attendance.setPunchOut(LocalTime.now());

        // Calculate working hours
        Duration duration = Duration.between(
                attendance.getPunchIn(),
                attendance.getPunchOut()
        );

        double hours = duration.toMinutes() / 60.0;
        attendance.setWorkingHours(hours);

        return attendanceRepository.save(attendance);
    }
    // AttendanceServiceImpl.java

    @Override
    public Attendance punchOutForToday(Long employeeId) {

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, today)
                .orElseThrow(() -> new ResourceNotFoundException("No attendance for today"));

        attendance.setPunchOut(LocalTime.now());

        Duration duration = Duration.between(attendance.getPunchIn(), attendance.getPunchOut());
        double hours = duration.toMinutes() / 60.0;
        attendance.setWorkingHours(hours);

        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceForEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeIdOrderByDateDesc(employeeId);
    }

}
