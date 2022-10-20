package dev.hammet.controllers;

import com.google.gson.Gson;
import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import io.javalin.http.Handler;
import org.eclipse.jetty.server.Authentication;

import javax.jws.soap.SOAPBinding;
import java.util.List;


class UserAndPassword {
    String username;
    String password;
}
public class EmployeeController {

    public Handler loginHandler = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        UserAndPassword userAndPassword = gson.fromJson(json, UserAndPassword.class);

        int ret = Driver.employeeService.authenticateUser(userAndPassword.username, userAndPassword.password);

        if(ret == 2){
            ctx.result("Logged in successfully as " + Driver.loggedInEmployee.getUsername() + " " +
                    (Driver.loggedInEmployee.isManager()? "with" : "without") + " manager privileges");

            ctx.status(Driver.loggedInEmployee.isManager()? 201 : 200);
        } else if (ret == 1) {
            ctx.status(400);
            ctx.result("Login unsuccessful: password mismatch" );
        }
        else{
            ctx.status(400);
            ctx.result("Login unsuccessful: user not found" );
        }

//        String username = ctx.pathParam("username").toString();
//        String password = ctx.pathParam("password").toString();
//        System.out.println("attempting login with username: " + username + " and password: " + password);
//        boolean success = false;
//        List<Employee> employeeList = Driver.employeeService.getAllEmployees();
//        for (Employee e : employeeList) {
//            System.out.println(e.toString());
//            if (e.getUsername().trim().equals(username.trim()) && e.getPassword().trim().equals(password.trim())) {
//                System.out.println("success!!");
//                Driver.loggedInEmployee = e;
//                success = true;
//
//            }
//        }
//        if(success){
//            ctx.result("Logged in successfully as " + Driver.loggedInEmployee.getUsername());
//            ctx.status(201);
//        }
//        else{
//            ctx.status(400);
//            ctx.result("Login unsuccessful");
//        }
    };

    public Handler createEmployeeHandler = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        Employee employee = gson.fromJson(json, Employee.class);
        Employee registeredEmployee = Driver.employeeService.createEmployee(employee);
        String employeeJson = gson.toJson(registeredEmployee);
        ctx.status(201);
        ctx.result(employeeJson);
    };

    public Handler getAllEmployees = (ctx) ->{
        List<Employee> employeeList = Driver.employeeService.getAllEmployees();
        Gson gson = new Gson();
        String json = gson.toJson(employeeList);
        ctx.result(json);
    };

    public Handler getEmployeeByIdHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));//This will take what value was in the {id} and turn it into an int for us to use
        Employee employee = Driver.employeeService.getEmployeeById(id);
        Gson gson = new Gson();
        String json = gson.toJson(employee);
        ctx.result(json);
    };

    public Handler updateEmployeeHandler = (ctx) ->{
        String employeeJSON = ctx.body();
        Gson gson = new Gson();
        Employee employee = gson.fromJson(employeeJSON, Employee.class);
        Employee updatedEmployee = Driver.employeeService.updateEmployee(employee);
        String json = gson.toJson(updatedEmployee);
        ctx.result(json);
    };


    public Handler deleteEmployeeHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean result = Driver.employeeService.deleteEmployeeById(id);
        if(result){
            ctx.status(204);
        }
        else{
            ctx.status(400);
            ctx.result("Could not process your delete request");
        }
    };
}
