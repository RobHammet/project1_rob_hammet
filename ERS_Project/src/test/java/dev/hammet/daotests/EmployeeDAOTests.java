package dev.hammet.daotests;

import dev.hammet.entities.Employee;
import dev.hammet.repositories.EmployeeDAO;
import dev.hammet.repositories.EmployeeDAOPostgres;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Random;

import java.util.List;

public class EmployeeDAOTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgres();

    // this is kabob case, tests are written in kabob case
    @Test
    @Order(1)
    void create_employee_test(){
        String randomName = "";
        Random random = new Random();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0; i < 10; i++) {
            int randNum = random.nextInt(26);
            randomName += alphabet[randNum];
        }
        Employee newEmployee = new Employee(0,randomName,"password",true);
        Employee savedEmployee = employeeDAO.createEmployee(newEmployee);
        System.out.println(savedEmployee);

        Assertions.assertNotEquals(0,savedEmployee.getId());
    }
    @Test
    @Order(2)
    void get_employee_by_id_test(){
        Employee gottenEmployee = employeeDAO.getEmployeeById(1);
        System.out.println(gottenEmployee);
        Assertions.assertNotNull(gottenEmployee);
    }
    @Test
    @Order(3)
    void get_employee_by_username_test(){
        Employee employee = employeeDAO.getEmployeeById(1);
        System.out.println(employee);

        String username = employee.getUsername();
        int id = employee.getId();

        Employee gottenEmployee = employeeDAO.getEmployeeByUsername(username);
        Assertions.assertEquals(id, gottenEmployee.getId());
    }
    @Test
    @Order(4)
    void get_all_employees_test(){

        List<Employee> allEmployees = employeeDAO.getAllEmployees();
        for (Employee e : allEmployees) {
            System.out.println(e.toString());
        }
        Assertions.assertTrue((allEmployees.size()> 0));
    }
    @Test
    @Order(5)
    void update_employee_test(){
        Employee employee = employeeDAO.getEmployeeById(1);
        Employee employee2 = new Employee(employee.getId(),employee.getUsername(),"newPassword",employee.isManager());
        employeeDAO.updateEmployee(employee2);
        Employee updatedEmployee = employeeDAO.getEmployeeById(1);
        Assertions.assertEquals("newPassword",updatedEmployee.getPassword());
    }
    @Test
    @Order(6)
    void change_role_test() {
        Employee employee = employeeDAO.getEmployeeByUsername("SomeGuy");
        boolean firstrole = employee.isManager();

        System.out.println(employee);

        employee = employeeDAO.changeEmployeeRole(employee, !firstrole);

        System.out.println(employee);
        Assertions.assertNotEquals(firstrole, employee.isManager());

    }
    @Test
    @Order(7)
    void append_photo_test() {
        Employee employee = employeeDAO.getEmployeeByUsername("SomeGuy");
        System.out.println(employee);
        byte[] bytes = new byte[50];
        employeeDAO.appendPhotoToEmployee(employee, bytes);
        Employee employee2 = employeeDAO.getEmployeeByUsername("SomeGuy");
        System.out.println(employee2);
        byte[] bytes2 =  employee2.getPhoto();
        Assertions.assertEquals(50, bytes2.length);
    }
    @Test
    @Order(8)
    void delete_employee_by_id_test(){
        boolean result = employeeDAO.deleteEmployeeById(8);
        Assertions.assertTrue(result);
    }


}
