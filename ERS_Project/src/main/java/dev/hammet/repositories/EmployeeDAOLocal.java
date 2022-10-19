package dev.hammet.repositories;

import dev.hammet.entities.Employee;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDAOLocal implements EmployeeDAO {
    private Map<Integer,Employee> employeeTable = new HashMap();
    private int idCount = 0;

    @Override
    public Employee createEmployee(Employee employee) {
        idCount++;
        employee.setId(idCount);
        employeeTable.put(employee.getId(), employee);
        System.out.println(employeeTable.values());
        return employee;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeTable.get(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeTable.put(employee.getId(), employee);
    }

    @Override
    public boolean deleteEmployeeById(int id) {
        Employee employee = employeeTable.remove(id); //This removes method returns the object that was removed from the map
        if(employee == null){
            return false;
        }
        else {
            return true;
        }
    }
}
