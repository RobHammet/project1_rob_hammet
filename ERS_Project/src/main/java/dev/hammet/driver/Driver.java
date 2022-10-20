package dev.hammet.driver;

import dev.hammet.controllers.EmployeeController;
import dev.hammet.controllers.ReimbursementRequestController;
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

    public static void main(String[] args) {


        Javalin app = Javalin.create();

        EmployeeController employeeController = new EmployeeController();
        app.post("/employees", employeeController.createEmployeeHandler);
        app.get("/employees/{id}", employeeController.getEmployeeByIdHandler);
        app.get("/employees", employeeController.getAllEmployees);
        app.put("/employees", employeeController.updateEmployeeHandler);
        app.delete("/employees/{id}", employeeController.deleteEmployeeHandler);

        ReimbursementRequestController reimbursementRequestController = new ReimbursementRequestController();
        app.post("/reimbursement_requests", reimbursementRequestController.createReimbursementRequestHandler);
        app.get("/reimbursement_requests/{r_id}", reimbursementRequestController.getReimbursementRequestByIdHandler);
        app.get("/reimbursement_requests", reimbursementRequestController.getAllReimbursementRequests);
        app.put("/reimbursement_requests", reimbursementRequestController.updateReimbursementRequestHandler);
        app.delete("/reimbursement_requests/{r_id}", reimbursementRequestController.deleteReimbursementRequestHandler);




        app.start();

    }
}