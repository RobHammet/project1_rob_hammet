package dev.hammet.services;

import dev.hammet.entities.Employee;
import dev.hammet.entities.ReimbursementRequest;

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
    Employee appendPhotoToEmployee(Employee employee, byte[] bytes);

    //DELETE
    boolean deleteEmployeeById(int id);

    int authenticateUser(String username, String password);

    Employee getEmployeeByUsername(String username);

    Employee registerNewUser(String username, String password);

    void logout();

}
