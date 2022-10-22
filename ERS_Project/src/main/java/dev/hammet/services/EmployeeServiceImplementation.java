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
        int ret = 0;
        List<Employee> employeeList = Driver.employeeService.getAllEmployees();
        for (Employee e : employeeList) {
            if (e.getUsername().trim().equals(username.trim())) {
                if (e.getPassword().trim().equals(password.trim())) {
                    Driver.loggedInEmployee = e;
                    ret = 2;
                    break;
                } else {
                    ret = 1;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public Employee registerNewUser(String username, String password) {
        Employee ret = new Employee(0, username, password, false);
        List<Employee> employeeList = Driver.employeeService.getAllEmployees();
        for (Employee e : employeeList) {
            if (e.getUsername().trim().equals(username.trim())) {
                ret = null;
                break;
            }
        }
        return ret;
    }





}
