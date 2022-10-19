package dev.hammet.driver;

import dev.hammet.controllers.EmployeeController;
import io.javalin.Javalin;
import dev.hammet.repositories.EmployeeDAOPostgres;
import dev.hammet.services.EmployeeService;
import dev.hammet.services.EmployeeServiceImplementation;

public class Driver {
    public static EmployeeService employeeService = new EmployeeServiceImplementation(new EmployeeDAOPostgres());

    public static void main(String[] args) {


        Javalin app = Javalin.create();

        EmployeeController employeeController = new EmployeeController();

        app.get("/employees/{id}", employeeController.getEmployeeByIdHandler);

        app.post("/employees", employeeController.createEmployeeHandler);

        app.put("/employees", employeeController.updateEmployeeHandler);

        app.delete("/employees/{id}", employeeController.deleteEmployeeHandler);

        app.start();

    }
}