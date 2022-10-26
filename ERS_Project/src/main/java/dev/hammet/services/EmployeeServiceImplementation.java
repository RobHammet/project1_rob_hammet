package dev.hammet.services;

import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import dev.hammet.repositories.EmployeeDAO;

import java.util.List;


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

        return savedEmployee;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return this.employeeDAO.getEmployeeById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeDAO.getAllEmployees();
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

    @Override
    public int authenticateUser(String username, String password) {
        int returnValue = 0;
        Employee checkEmployee = Driver.employeeService.getEmployeeByUsername(username);
        if (checkEmployee != null) {
            if (checkEmployee.getPassword().trim().equals( password.trim())) {
                Driver.loggedInEmployee = checkEmployee;
                returnValue = 2;

            } else {
                returnValue = 1;
            }
        } else {
            returnValue = 0;
        }
        return returnValue;

    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return this.employeeDAO.getEmployeeByUsername(username);
    }

    @Override
    public Employee registerNewUser(String username, String password) {
        Employee returnEmployee = null;
        Employee checkEmployee = Driver.employeeService.getEmployeeByUsername(username);
        if (checkEmployee == null) {
            returnEmployee = new Employee(0, username, password, false);
            if (username.toLowerCase().contains("admin"))
                returnEmployee.setManager(true);
        }
        return returnEmployee;
    }

    @Override
    public void logout() {

        Driver.loggedInEmployee = null;
        System.gc();
        System.runFinalization();

    }


}
