package dev.hammet.repositories;

import dev.hammet.entities.Employee;

import java.util.List;


public interface EmployeeDAO {

    //CREATE
    Employee createEmployee(Employee employee);

    //READ
    Employee getEmployeeById(int id);
    Employee getEmployeeByUsername(String username);
    List<Employee> getAllEmployees();

    //UPDATE
    Employee updateEmployee(Employee employee);
    Employee changeEmployeeRole(Employee employee, boolean toManager);

    Employee appendPhotoToEmployee(Employee employee, byte[] bytes);

    //DELETE
    boolean deleteEmployeeById(int id);



}
