package com.seams.employee_system.service;

import com.seams.employee_system.entity.Employee;
import com.seams.employee_system.exception.DuplicateEmailException;
import com.seams.employee_system.exception.DuplicatePhoneException;
import com.seams.employee_system.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee addEmployee(Employee employee) {

        if (repository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEmailException("Email already exists!");
        }

        if (repository.existsByPhone(employee.getPhone())) {
            throw new DuplicatePhoneException("Phone number already exists!");
        }

        return repository.save(employee);
    }

    @Override
    public Page<Employee> getAllEmployeesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
    @Override
    public Page<Employee> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }
    @Override
    public Page<Employee> searchByDepartment(String department, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByDepartmentIgnoreCase(department, pageable);
    }
    @Override
    public Page<Employee> searchByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByEmailContainingIgnoreCase(email, pageable);
    }
    @Override
    public Page<Employee> getAllEmployeesSorted(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findAll(pageable);
    }
    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }



    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
}
