package dev.hammet.services;

import dev.hammet.entities.Employee;

import java.util.List;


public interface EmployeeService {
    //CREATE
    Employee createEmployee(Employee employee);
    //READ
    Employee getEmployeeById(int id);

    List<Employee> getAllEmployees();
    //UPDATE
    Employee updateEmployee(Employee employee);
    //DELETE
    boolean deleteEmployeeById(int id);

}
