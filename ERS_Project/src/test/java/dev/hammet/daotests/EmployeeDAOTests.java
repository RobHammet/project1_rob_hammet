package dev.hammet.daotests;

import dev.hammet.entities.Employee;
import dev.hammet.repositories.EmployeeDAO;
import dev.hammet.repositories.EmployeeDAOPostgres;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class EmployeeDAOTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgres();

    // this is kabob case, tests are written in kabob case
    @Test
    @Order(1)
    void create_employee_test(){
        Employee newEmployee = new Employee(0,"TomAnderson","hunter2",true);
        Employee savedEmployee = employeeDAO.createEmployee(newEmployee);
        Assertions.assertNotEquals(0,savedEmployee.getId());
    }
    @Test
    @Order(2)
    void get_employee_by_id_test(){
        Employee gottenEmployee = employeeDAO.getEmployeeById(1);
        Assertions.assertEquals("hunter2",gottenEmployee.getPassword());
    }
    @Test
    @Order(3)
    void update_employee_test(){
        //When testing update, you should either get the book and use its values or create a completely new book and use those values
        Employee employee = employeeDAO.getEmployeeById(1);
        //You can think of update more of a full replacement/swap and less of specific values being update
        Employee employee2 = new Employee(employee.getId(),employee.getUsername(),"hunter3",employee.isManager());
        employeeDAO.updateEmployee(employee2);
        Employee updatedEmployee = employeeDAO.getEmployeeById(employee2.getId());
        Assertions.assertEquals("hunter3",updatedEmployee.getPassword());
    }
    @Test
    @Order(4)
    void delete_employee_by_id_test(){
        boolean result = employeeDAO.deleteEmployeeById(1);
        Assertions.assertTrue(result);
    }


}
