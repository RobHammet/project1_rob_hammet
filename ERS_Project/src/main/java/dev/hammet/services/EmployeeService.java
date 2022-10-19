package dev.hammet.services;

import dev.hammet.entities.Employee;

public interface EmployeeService {
    //CREATE
    Employee createEmployee(Employee employee);
    //READ
    Employee getEmployeeById(int id);
    //UPDATE
    Employee updateEmployee(Employee employee);
    //DELETE
    boolean deleteEmployeeById(int id);

}
