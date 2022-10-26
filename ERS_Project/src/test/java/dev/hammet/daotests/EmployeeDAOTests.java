package dev.hammet.daotests;

import dev.hammet.entities.Employee;
import dev.hammet.repositories.EmployeeDAO;
import dev.hammet.repositories.EmployeeDAOPostgres;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeDAOTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgres();

    // this is kabob case, tests are written in kabob case
    @Test
    @Order(1)
    void create_employee_test(){
        Employee newEmployee = new Employee(0,"SomeGuy","password",true);
        Employee savedEmployee = employeeDAO.createEmployee(newEmployee);
        Assertions.assertNotEquals(0,savedEmployee.getId());
    }
    @Test
    @Order(2)
    void get_employee_by_id_test(){
        Employee gottenEmployee = employeeDAO.getEmployeeById(1);
        Assertions.assertNotNull(gottenEmployee);
    }
    @Test
    @Order(3)
    void get_employee_by_username_test(){
        Employee gottenEmployee = employeeDAO.getEmployeeByUsername("SomeGuy");
        Assertions.assertEquals("password",gottenEmployee.getPassword());
    }
    @Test
    @Order(4)
    void get_all_employees_test(){

        List<Employee> allEmployees = employeeDAO.getAllEmployees();
        for (Employee e : allEmployees) {
            System.out.println(e.toString());
        }
        Assertions.assertTrue((allEmployees.size()> 1));
    }
    @Test
    @Order(5)
    void update_employee_test(){
        Employee employee = employeeDAO.getEmployeeByUsername("SomeGuy");
        Employee employee2 = new Employee(employee.getId(),employee.getUsername(),"hunter2",employee.isManager());
        employeeDAO.updateEmployee(employee2);
        Employee updatedEmployee = employeeDAO.getEmployeeByUsername("SomeGuy");
        Assertions.assertEquals("hunter2",updatedEmployee.getPassword());
    }
    @Test
    @Order(6)
    void delete_employee_by_id_test(){
        boolean result = employeeDAO.deleteEmployeeById(8);
        Assertions.assertTrue(result);
    }


}
