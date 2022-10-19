package dev.hammet.repositories;

import dev.hammet.entities.Employee;


public interface EmployeeDAO {

    //CREATE
    Employee createEmployee(Employee employee);

    //READ
    Employee getEmployeeById(int id);

    //UPDATE
    Employee updateEmployee(Employee employee);

    //DELETE
    boolean deleteEmployeeById(int id);


}
