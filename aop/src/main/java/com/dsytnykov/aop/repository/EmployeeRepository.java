package com.dsytnykov.aop.repository;

import com.dsytnykov.aop.model.Employee;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class EmployeeRepository {
    private final List<Employee> employees = Arrays.asList(
            new Employee(1, "abc", 28, 123, "F", "HR", "Blore", 2020),
            new Employee(2, "xyz", 29, 120, "F", "HR", "Hyderabad", 2015),
            new Employee(3, "efg", 30, 115, "M", "HR", "Chennai", 2014),
            new Employee(4, "def", 32, 125, "F", "HR", "Chennai", 2013),
            new Employee(5, "ijk", 22, 150, "F", "IT", "Noida", 2013),
            new Employee(6, "mno", 27, 140, "M", "IT", "Gurugram", 2017),
            new Employee(7, "uvw", 26, 130, "F", "IT", "Pune", 2016),
            new Employee(8, "pqr", 23, 145, "M", "IT", "Trivandam", 2015),
            new Employee(9, "stv", 25, 160, "M", "IT", "Blore", 2010)
    );

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(int id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst().orElseThrow(() -> new NoSuchElementException("Employee not found"));
    }
}
