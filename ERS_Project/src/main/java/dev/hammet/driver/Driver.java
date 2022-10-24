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
        ReimbursementRequestController reimbursementRequestController = new ReimbursementRequestController();


        // any user can use without login
        // --- employee-related:
        app.post("/register", employeeController.registerNewUserHandler);
        app.post("/login", employeeController.loginHandler);

        // any registered employee can user after login (own information and own requests)
        // --- employee-related:
        app.get("/logout", employeeController.logoutHandler);
        app.get("/profile", employeeController.loadProfileHandler);
        app.put("/update-profile", employeeController.updateProfileHandler);
        app.get("/my-requests", reimbursementRequestController.showUsersOwnRequestsHandler);

//        app.get("/get_employee/{id}", employeeController.getEmployeeByIdHandler);
//        app.put("/update_employee", employeeController.updateEmployeeHandler);

        // --- request-related:
        app.post("/make_new_reimbursement_request", reimbursementRequestController.makeNewReimbursementRequestHandler);
//        app.post("/new_reimbursement_request", reimbursementRequestController.createReimbursementRequestHandler);
//        app.get("/get_reimbursement_requests_for/{id}", reimbursementRequestController.getReimbursementRequestsForEmployeeHandler);


        // only logged in managers can use (other employees' requests)
        app.before("/manager/*", employeeController.checkManagerHandler);
        // --- employee-related:
        app.get("/manager/get_all_employees", employeeController.getAllEmployees);
     //   app.delete("/delete_employee/{id}", employeeController.deleteEmployeeHandler);
        // --- request-related:
        app.get("/get_reimbursement_request/{id}", reimbursementRequestController.getReimbursementRequestByIdHandler);
        app.get("/get_reimbursement_requests_for/{id}", reimbursementRequestController.getReimbursementRequestsForEmployeeHandler);
        app.get("/manager/get_all_reimbursement_requests", reimbursementRequestController.getAllReimbursementRequestsHandler);
        app.get("/manager/get_pending_reimbursement_requests", reimbursementRequestController.getPendingReimbursementRequestsHandler);
        app.put("/update_reimbursement_request", reimbursementRequestController.updateReimbursementRequestHandler);
        app.delete("/delete_reimbursement_request/{id}", reimbursementRequestController.deleteReimbursementRequestHandler);

        app.put("/manager/change_reimbursement_request_status/{id}-{st}", reimbursementRequestController.changeReimbursementRequestStatusHandler);


        app.start();

    }
}