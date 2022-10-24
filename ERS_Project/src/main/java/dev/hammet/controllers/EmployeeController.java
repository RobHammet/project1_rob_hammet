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
    UserAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class EmployeeController {


    public Handler registerNewUserHandler = (ctx) ->{

        String json = ctx.body();
        Gson gson = new Gson();
        UserAndPassword userAndPassword = gson.fromJson(json, UserAndPassword.class);

        try {
            Employee newUser = Driver.employeeService.registerNewUser(userAndPassword.username, userAndPassword.password);
            if (newUser == null) {
                ctx.status(401);
                ctx.result("Register unsuccessful: username already exists" );
            } else {
                Employee registeredEmployee = Driver.employeeService.createEmployee(newUser);
                ctx.status(201);
                ctx.result("Successfully registered account of username: " + registeredEmployee.getUsername() );
            }
        } catch (RuntimeException e) {
            ctx.status(400);
            ctx.result("Register unsuccessful: " + e.getMessage() );
        }
    };

    public Handler loginHandler = (ctx) ->{
        if(Driver.loggedInEmployee == null){

            String json = ctx.body();
            Gson gson = new Gson();
            UserAndPassword userAndPassword = gson.fromJson(json, UserAndPassword.class);

            int ret = Driver.employeeService.authenticateUser(userAndPassword.username, userAndPassword.password);

            if(ret == 2){
                ctx.result("Logged in successfully as " + Driver.loggedInEmployee.getUsername() + " *" +
                        (Driver.loggedInEmployee.isManager()? "with" : "without") + "* manager privileges");

                ctx.status(Driver.loggedInEmployee.isManager()? 201 : 200);
            } else if (ret == 1) {
                ctx.status(400);
                ctx.result("Login unsuccessful: password mismatch" );
            }
            else{
                ctx.status(400);
                ctx.result("Login unsuccessful: user not found" );
            }
        } else {
            ctx.status(400);
            ctx.result("A user is already logged in" );
        }

    };

    public Handler logoutHandler = (ctx) ->{
        System.out.println("ATTEMPTING LOG OUT...");
        if(Driver.loggedInEmployee != null){
            String username = Driver.loggedInEmployee.getUsername();

            Driver.loggedInEmployee = null;
            System.gc();
            System.runFinalization();

            ctx.result(username + " logged out successfully!");
            ctx.status(200);
            System.out.println("LOGGED OUT");
        } else{
            ctx.status(400);
            ctx.result("Cannot log out: no user logged in" );
            System.out.println("LOG OUT FAILED");
        }

    };
    public Handler loadProfileHandler = (ctx) ->{

        if (Driver.loggedInEmployee != null) {

            Employee employee = Driver.employeeService.getEmployeeById(Driver.loggedInEmployee.getId());
            UserAndPassword userAndPassword = new UserAndPassword(employee.getUsername(), employee.getPassword());
            Gson gson = new Gson();
            String json = gson.toJson(userAndPassword);
            ctx.result(json);

        }
    };

    public Handler updateProfileHandler = (ctx) ->{

        String json = ctx.body();
        Gson gson = new Gson();
        UserAndPassword userAndPassword = gson.fromJson(json, UserAndPassword.class);

        try {
            // make a new employee object based on logged-in user, updating user & pwd
            Employee modifiedEmployee = new Employee(Driver.loggedInEmployee.getId(),
                                                    userAndPassword.username,
                                                    userAndPassword.password,
                                                    Driver.loggedInEmployee.isManager());
            Employee updatedEmployee = Driver.employeeService.updateEmployee(modifiedEmployee);
            Driver.loggedInEmployee = updatedEmployee;


            ctx.status(201);
            ctx.result("Successfully updated profile of: " + Driver.loggedInEmployee.getUsername() );

        } catch (RuntimeException e) {
            ctx.status(400);
            ctx.result("Profile update unsuccessful: " + e.getMessage() );
        }
    };

    public Handler checkManagerHandler = (ctx) ->{
        if (!Driver.loggedInEmployee.isManager()) {

            throw new RuntimeException("Not a manager, throwing...");
        }
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
