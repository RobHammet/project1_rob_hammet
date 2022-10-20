package dev.hammet.controllers;

import com.google.gson.Gson;
import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import io.javalin.http.Handler;

import java.util.List;

public class EmployeeController {

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
