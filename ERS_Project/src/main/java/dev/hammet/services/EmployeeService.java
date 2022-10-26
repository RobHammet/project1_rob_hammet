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
    Employee changeEmployeeRole(Employee employee, boolean toManager);
    //DELETE
    boolean deleteEmployeeById(int id);

    int authenticateUser(String username, String password);

    Employee getEmployeeByUsername(String username);

    Employee registerNewUser(String username, String password);

    void logout();

}
