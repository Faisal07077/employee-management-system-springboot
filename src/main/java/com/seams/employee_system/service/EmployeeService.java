package com.seams.employee_system.service;

import com.seams.employee_system.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Page<Employee> getAllEmployeesPaginated(int page, int size);

    Page<Employee> searchByName(String name, int page, int size);

    Page<Employee> searchByDepartment(String department, int page, int size);

    Page<Employee> searchByEmail(String email, int page, int size);

    Page<Employee> getAllEmployeesSorted(int page, int size, String sortBy, String sortDir);

    void  deleteEmployee(Long id);
}
