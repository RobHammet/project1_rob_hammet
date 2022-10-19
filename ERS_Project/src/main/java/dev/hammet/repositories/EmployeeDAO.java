package dev.hammet.repositories;

import dev.hammet.entities.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {

    //CREATE
    Employee createEmployee(Employee employee);

    //READ
    Employee getEmployeeById(int id) throws SQLException;

    //UPDATE
    Employee updateEmployee(Employee employee);

    //DELETE
    boolean deleteEmployeeById(int id);


}
