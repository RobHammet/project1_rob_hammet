package dev.hammet.services;

import dev.hammet.entities.Employee;
import dev.hammet.repositories.EmployeeDAO;


public class EmployeeServiceImplementation implements EmployeeService {


    private EmployeeDAO employeeDAO;
    public EmployeeServiceImplementation(EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }


    @Override
    public Employee createEmployee(Employee employee) {
        if(employee.getUsername().length() == 0){
            throw new RuntimeException("Username cannot be empty");
        }
        if(employee.getPassword().length() == 0){
            throw new RuntimeException("Password cannot be empty");
        }
        Employee savedEmployee = this.employeeDAO.createEmployee(employee);

        System.out.println(savedEmployee.toString());

        return savedEmployee;
    }

    @Override
    public Employee getEmployeeById(int id) {
       // try{
            return this.employeeDAO.getEmployeeById(id);
//        } catch(SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if(employee.getUsername().length() == 0){
            throw new RuntimeException("Username cannot be empty");
        }
        if(employee.getPassword().length() == 0){
            throw new RuntimeException("Password cannot be empty");
        }
        return this.employeeDAO.updateEmployee(employee);
    }

    @Override
    public boolean deleteEmployeeById(int id) {
        return this.employeeDAO.deleteEmployeeById(id);
    }
}
