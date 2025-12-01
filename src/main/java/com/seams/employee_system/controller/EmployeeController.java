package com.seams.employee_system.controller;

import com.seams.employee_system.entity.Employee;
import com.seams.employee_system.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
@Validated
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // ✅ ADD EMPLOYEE WITH BODY VALIDATION
    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody Employee employee) {
        service.addEmployee(employee);
        return new ResponseEntity<>("Employee added successfully ", HttpStatus.CREATED);
    }

    // ✅ PAGINATION WITH VALIDATION
    @GetMapping("/page")
    public Page<Employee> getAllEmployeesPaginated(
            @RequestParam @Min(value = 0, message = "Page must be 0 or greater") int page,
            @RequestParam @Min(value = 1, message = "Size must be at least 1") int size) {

        return service.getAllEmployeesPaginated(page, size);
    }

    // ✅ SEARCH BY NAME WITH VALIDATION
    @GetMapping("/search/name")
    public Page<Employee> searchByName(
            @RequestParam @NotBlank(message = "Name is required") String name,
            @RequestParam @Min(value = 0, message = "Page must be 0 or greater") int page,
            @RequestParam @Min(value = 1, message = "Size must be at least 1") int size) {

        return service.searchByName(name, page, size);
    }

    // ✅ SEARCH BY DEPARTMENT WITH VALIDATION
    @GetMapping("/search/department")
    public Page<Employee> searchByDepartment(
            @RequestParam @NotBlank(message = "Department is required") String department,
            @RequestParam @Min(value = 0, message = "Page must be 0 or greater") int page,
            @RequestParam @Min(value = 1, message = "Size must be at least 1") int size) {

        return service.searchByDepartment(department, page, size);
    }

    // ✅ SEARCH BY EMAIL WITH STRICT VALIDATION
    @GetMapping("/search/email")
    public Page<Employee> searchByEmail(
            @RequestParam
            @Pattern(
                    regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                    message = "Invalid email format"
            ) String email,
            @RequestParam @Min(value = 0, message = "Page must be 0 or greater") int page,
            @RequestParam @Min(value = 1, message = "Size must be at least 1") int size) {

        return service.searchByEmail(email, page, size);
    }

    // ✅ PAGINATION + SORTING WITH VALIDATION
    @GetMapping("/page-sort")
    public Page<Employee> getAllEmployeesSorted(
            @RequestParam @Min(value = 0, message = "Page must be 0 or greater") int page,
            @RequestParam @Min(value = 1, message = "Size must be at least 1") int size,
            @RequestParam @NotBlank(message = "Sort field is required") String sortBy,
            @RequestParam
            @Pattern(regexp = "asc|desc", message = "Sort direction must be 'asc' or 'desc'")
            String sortDir) {

        return service.getAllEmployeesSorted(page, size, sortBy, sortDir);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }


    // ✅ GET ALL EMPLOYEES (NO PAGINATION)
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }
}
