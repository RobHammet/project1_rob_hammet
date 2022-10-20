package dev.hammet.driver;

import dev.hammet.controllers.EmployeeController;
import dev.hammet.controllers.ReimbursementRequestController;
import dev.hammet.entities.Employee;
import dev.hammet.repositories.ReimbursementRequestDAOPostgres;
import dev.hammet.services.ReimbursementRequestService;
import dev.hammet.services.ReimbursementRequestServiceImpl;
import io.javalin.Javalin;
import dev.hammet.repositories.EmployeeDAOPostgres;
import dev.hammet.services.EmployeeService;
import dev.hammet.services.EmployeeServiceImplementation;

public class Driver {
    public static EmployeeService employeeService = new EmployeeServiceImplementation(new EmployeeDAOPostgres());

    public static ReimbursementRequestService reimbursementRequestService  = new ReimbursementRequestServiceImpl(new ReimbursementRequestDAOPostgres());

    public static Employee loggedInEmployee = null;
    public static void main(String[] args) {


        Javalin app = Javalin.create();

        EmployeeController employeeController = new EmployeeController();

        //app.post("/login/{username}/{password}", employeeController.loginHandler);
        app.post("/login/", employeeController.loginHandler);

        app.post("/new_employee", employeeController.createEmployeeHandler);
        app.get("/get_employee/{id}", employeeController.getEmployeeByIdHandler);
        app.get("/get_all_employees", employeeController.getAllEmployees);
        app.put("/update_employee", employeeController.updateEmployeeHandler);
        app.delete("/delete_employee/{id}", employeeController.deleteEmployeeHandler);

        ReimbursementRequestController reimbursementRequestController = new ReimbursementRequestController();
        app.post("/new_reimbursement_request", reimbursementRequestController.createReimbursementRequestHandler);
        app.get("/get_reimbursement_request/{id}", reimbursementRequestController.getReimbursementRequestByIdHandler);
        app.get("/get_all_reimbursement_requests", reimbursementRequestController.getAllReimbursementRequests);
        app.put("/update_reimbursement_request", reimbursementRequestController.updateReimbursementRequestHandler);
        app.delete("/delete_reimbursement_request/{id}", reimbursementRequestController.deleteReimbursementRequestHandler);




        app.start();

    }
}