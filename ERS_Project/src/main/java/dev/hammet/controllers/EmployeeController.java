package dev.hammet.controllers;

import com.google.gson.Gson;
import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import dev.hammet.entities.ReimbursementRequest;
import io.javalin.http.Handler;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
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



        try {
            String json = ctx.body();
            Gson gson = new Gson();
            UserAndPassword userAndPassword = gson.fromJson(json, UserAndPassword.class);
            Employee newUser = Driver.employeeService.registerNewUser(userAndPassword.username, userAndPassword.password);
            if (newUser == null) {
                ctx.status(401);
                ctx.result("Register unsuccessful: username already exists" );
            } else {
                Employee registeredEmployee = Driver.employeeService.createEmployee(newUser);
                ctx.status(201);
                ctx.result("Successfully registered account of username: " + registeredEmployee.getUsername() +
                        (registeredEmployee.isManager() ? "as ADMIN": ""));
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
                        (Driver.loggedInEmployee.isManager()? "with" : "without") + "* ADMIN privileges");

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
        if(Driver.loggedInEmployee != null){

            String username = Driver.loggedInEmployee.getUsername();

            Driver.employeeService.logout();

            ctx.result(username + " logged out successfully!");
            ctx.status(200);
        } else{
            ctx.status(400);
            ctx.result("Cannot log out: no user logged in" );
        }

    };


    public Handler loadProfileHandler = (ctx) ->{

        if (Driver.loggedInEmployee != null) {

            Employee employee = Driver.employeeService.getEmployeeById(Driver.loggedInEmployee.getId());
           // UserAndPassword userAndPassword = new UserAndPassword(employee.getUsername(), employee.getPassword());

           // Gson gson = new Gson();
           // String json = gson.toJson(userAndPassword);
           // ctx.result(json);
            ctx.result(makeHtmlTableOutputOfEmployeeProfile(employee));

        }
    };



    private String makeHtmlTableOutputOfEmployeeProfile(Employee employee) throws UnsupportedEncodingException {

        String html = "<html>\n" +
                "<head><style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-radius: 10px;\n" +
                "}\n" +
                "</style></head>" +
                "<body bgcolor=lightgray>\n" +
                "\n" +
                "    <h3>Profile</h3>\n" +
                "    \n" +
                "    <table style=\"width:100%\">\n" +
                "        <tr>\n" +
                "            <th>Username</th>\n" +
                "            <th>Level</th>\n" +
                "            <th>First Name</th>\n" +
                "            <th>Last Name</th>\n" +
                "            <th>Email</th>\n" +
                "            <th>Photo</th>\n" +
                "        </tr>\n"  +
                "            <td>" + employee.getUsername() +"</td>\n" +
                "            <td>" + (employee.isManager()? "Manager" : "Employee") + "</td>\n" +
                "            <td>" + employee.getFirstname() + "</td>\n" +
                "            <td>" + employee.getLastname() + "</td>\n" +
                "            <td>" + employee.getEmail() + "</td>\n" +
                "            <td>" + (employee.getPhoto() != null? "<img src=\"" + "data:image/jpeg;base64," + bytesToString(employee.getPhoto()) + "\" height=50 width=50>" : "no image") +
                "</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";

        return html;
    }
    private String bytesToString (byte[] bytes) throws UnsupportedEncodingException {
        if (bytes == null)
            return "";
        if (bytes.length == 0)
            return "";

        Base64.Encoder encoder = Base64.getEncoder();
        String str = new String(encoder.encode(bytes), "UTF-8");

        return str;
    }
    public Handler updateProfileHandler = (ctx) ->{

        try {

            String json = ctx.body();
            Gson gson = new Gson();
            Employee employee = gson.fromJson(json, Employee.class);


            Employee updatedEmployee = Driver.employeeService.updateEmployee(employee);
            Driver.loggedInEmployee = updatedEmployee;


            ctx.status(201);
            ctx.result("Successfully updated profile of: " + Driver.loggedInEmployee.getUsername() );

        } catch (RuntimeException e) {
            ctx.status(400);
            ctx.result("Profile update unsuccessful: " + e.getMessage() );
        }
    };


    public Handler uploadPhotoHandler = (ctx) ->{

        byte[] bytes = ctx.bodyAsBytes();  // ctx.body().getBytes();

        Employee employee = Driver.loggedInEmployee;
        Employee updatedEmployee = Driver.employeeService.appendPhotoToEmployee(employee, bytes);
     //   ReimbursementRequest reimbursementRequest = Driver.reimbursementRequestService.getReimbursementRequestById(id);
     //   ReimbursementRequest updatedReimbursementRequest = Driver.reimbursementRequestService.appendReceiptToReimbursementRequest(reimbursementRequest, bytes);

        bytes = null;
        bytes = employee.getPhoto();

        String encodedFile = bytesToString(bytes);


        String html = "<html><h2>Profile photo set</h2><br><img src=\"data:image/jpeg;base64," + encodedFile + "\"></html>";
        ctx.result(html);


    };

    public Handler getAllEmployees = (ctx) ->{
        List<Employee> employeeList = Driver.employeeService.getAllEmployees();
        Gson gson = new Gson();
        String json = gson.toJson(employeeList);
        ctx.result(json);
    };

    public Handler checkManagerHandler = (ctx) ->{

        if (!Driver.loggedInEmployee.isManager())
            ctx.redirect("/kick");
    };

    public Handler kickOutNonManagerHandler = (ctx) ->{
        ctx.result("Access denied: not an ADMIN");
        ctx.status(400);
    };

    public Handler changeRoleHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean toManager =  (Integer.parseInt(ctx.pathParam("role")) >= 1);

        Employee employee = Driver.employeeService.getEmployeeById(id); //gson.fromJson(employeeJSON, Employee.class);
        Employee changedEmployee = Driver.employeeService.changeEmployeeRole(employee, toManager);

        if (changedEmployee == null) {
            ctx.result("Failed to change role of " + changedEmployee.getUsername());
            ctx.status(400);
        } else {

            ctx.result("Changed role of " + changedEmployee.getUsername() + " " + (toManager?"to ADMIN":"to EMPLOYEE") );
            ctx.status(200);
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
    public Handler getEmployeeByIdHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
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
