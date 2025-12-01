package com.seams.employee_system.service;

import com.seams.employee_system.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    Attendance punchIn(Long employeeId);

    Attendance punchOut(Long attendanceId);
    Attendance punchOutForToday(Long employeeId);
    List<Attendance> getAttendanceForEmployee(Long employeeId);

}
