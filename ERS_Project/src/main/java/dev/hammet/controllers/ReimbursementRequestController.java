package dev.hammet.controllers;

import com.google.gson.Gson;
import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import dev.hammet.entities.ReimbursementRequest;
import io.javalin.http.Handler;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;


public class ReimbursementRequestController {


    public Handler makeNewReimbursementRequestHandler = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        ReimbursementRequest newRequest = gson.fromJson(json, ReimbursementRequest.class);

//        ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
//        reimbursementRequest.setEmployeeId(Driver.loggedInEmployee.getId());
//        reimbursementRequest.setAmount(newRequest.getAmount());
//        reimbursementRequest.setDescription(newRequest.getDescription());
//        reimbursementRequest.setType(newRequest.getType());
//        reimbursementRequest.setReceiptImage(null);

        try {
            ReimbursementRequest registeredReimbursementRequest = Driver.reimbursementRequestService.createReimbursementRequest(newRequest);
            String reimbursementRequestJson = gson.toJson(registeredReimbursementRequest);
            ctx.status(201);
            ctx.result(reimbursementRequestJson);
        } catch (RuntimeException e) {
            e.printStackTrace();
            ctx.status(400);
            ctx.result(e.getMessage());
        }

    };

    public Handler getAllReimbursementRequestsHandler = (ctx) ->{
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getAllReimbursementRequests();
        Gson gson = new Gson();
        String json = gson.toJson(reimbursementRequestList);
        ctx.result(json);

        ctx.result(makeHtmlTableOutputOfRequestList(reimbursementRequestList));

    };

    private String makeHtmlTableOutputOfRequestList(List<ReimbursementRequest> requestList) throws UnsupportedEncodingException{
        String html = "<html>\n" +
                "<head><style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-radius: 10px;\n" +
                "}\n" +
                "</style></head>" +
                "<body bgcolor=lightgray>\n" +
                "\n" +
                "    <h3>Reimbursement Requests</h3>\n" +
                "    \n" +
                "    <table style=\"width:100%\">\n" +
                "        <tr>\n" +
                "            <th>Employee</th>\n" +
                "            <th>Type</th>\n" +
                "            <th>Description</th>\n" +
                "            <th>Amount</th>\n" +
                "            <th>Status</th>\n" +
                "            <th>Receipt</th>\n" +
                "        </tr>\n" ;

        for (ReimbursementRequest r: requestList) {


            html +=     "        <tr>\n" +
                    "            <td>" + Driver.employeeService.getEmployeeById(r.getEmployeeId()).getUsername() + " (ID: " + r.getEmployeeId() +")</td>\n" +
                    "            <td>" + r.getType() + "</td>\n" +
                    "            <td>" + r.getDescription() + "</td>\n" +
                    "            <td>" +r.getAmount() + "</td>\n" +
                    "            <td>" + r.getStatus() + "</td>\n" +
                    "            <td>" + (r.getReceiptImage() != null? "<img src=\"" + "data:image/jpeg;base64," + bytesToString(r.getReceiptImage()) + "\" height=50 width=50>" : "no image") +
                    "</td>\n" +
                    "        </tr>\n";
        }

        html += "    </table>\n" +
                "</body>\n" +
                "</html>";

        return html;
    }

    private String makeHtmlTableOutputOfOwnRequestList(List<ReimbursementRequest> requestList) throws UnsupportedEncodingException{
        String html = "<html>\n" +
                "<head><style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-radius: 10px;\n" +
                "}\n" +
                "</style></head>" +
                "<body bgcolor=lightgray>\n" +
                "\n" +
                "    <h3>My Reimbursement Requests</h3>\n" +
                "    \n" +
                "    <table style=\"width:100%\">\n" +
                "        <tr>\n" +
                "            <th>Request ID #</th>\n" +
                "            <th>Type</th>\n" +
                "            <th>Description</th>\n" +
                "            <th>Amount</th>\n" +
                "            <th>Status</th>\n" +
                "            <th>Receipt</th>\n" +
                "        </tr>\n" ;

        for (ReimbursementRequest r: requestList) {


            html +=     "        <tr>\n" +
                    "            <td>" + r.getId() + "</td>\n" +
                    "            <td>" + r.getType() + "</td>\n" +
                    "            <td>" + r.getDescription() + "</td>\n" +
                    "            <td>" +r.getAmount() + "</td>\n" +
                    "            <td>" + r.getStatus() + "</td>\n" +
                    "            <td>" + (r.getReceiptImage() != null? "<img src=\"" + "data:image/jpeg;base64," + bytesToString(r.getReceiptImage()) + "\" height=50 width=50>" : "no image") +
                    "</td>\n" +
                    "        </tr>\n";
        }

        html += "    </table>\n" +
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


    public Handler getPendingReimbursementRequestsOfTypeHandler = (ctx) ->{
        ReimbursementRequest.Type type = ReimbursementRequest.Type.valueOf(String.valueOf(ctx.pathParam("type")));
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getPendingReimbursementRequestsOfType(type);

       // Gson gson = new Gson();
       // String json = gson.toJson(reimbursementRequestList);
       // ctx.result(json);
        String html = makeHtmlTableOutputOfRequestList(reimbursementRequestList);
        ctx.result(html);
    };

    public Handler getPendingReimbursementRequestsHandler = (ctx) ->{
        System.out.println("getPendingReimbursementRequestsHandler");
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getAllPendingReimbursementRequests();
//        Gson gson = new Gson();
//        String json = gson.toJson(reimbursementRequestList);
//        ctx.result(json);
        String html = makeHtmlTableOutputOfRequestList(reimbursementRequestList);
        ctx.result(html);
    };
    public Handler getReimbursementRequestsForEmployeeHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getReimbursementRequestsForEmployee(id);
        Gson gson = new Gson();
        String json = gson.toJson(reimbursementRequestList);
        ctx.result(json);
    };
    public Handler showUsersOwnRequestsOfTypeHandler = (ctx) ->{
        int id = Driver.loggedInEmployee.getId();
        ReimbursementRequest.Type type = ReimbursementRequest.Type.valueOf(String.valueOf(ctx.pathParam("type")));
        System.out.println("::" + type.name());
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getReimbursementRequestsForEmployeeOfType(id, type);
//        Gson gson = new Gson();
//        String json = gson.toJson(reimbursementRequestList);
//        ctx.result(json);
        String html = makeHtmlTableOutputOfOwnRequestList(reimbursementRequestList);
        ctx.result(html);
    };
    public Handler showUsersOwnRequestsHandler = (ctx) ->{
        System.out.println("ATTEMPTING TO OPEN USER'S OWN REQUESTS");
        int id = Driver.loggedInEmployee.getId();
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getReimbursementRequestsForEmployee(id);
        System.out.println(reimbursementRequestList);
//        Gson gson = new Gson();
//        String json = gson.toJson(reimbursementRequestList);
//        ctx.result(json);
        String html = makeHtmlTableOutputOfOwnRequestList(reimbursementRequestList);
        ctx.result(html);
    };

    public Handler changeReimbursementRequestStatusHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        int statusInt = Integer.parseInt(ctx.pathParam("st"));
        ReimbursementRequest.Status status = null;
        if (statusInt == 0) {
            status = ReimbursementRequest.Status.PENDING;
        } else if (statusInt == 1) {
            status = ReimbursementRequest.Status.APPROVED;
        } else if (statusInt == 2) {
            status = ReimbursementRequest.Status.DENIED;
        } else {
            status = null;
        }

        boolean result = false;
        try {
            result = Driver.reimbursementRequestService.changeReimbursementRequestStatus(id, status);
        } catch (RuntimeException e) {
            result = false;
        }

        if(result){
            ctx.status(204);
            ctx.result("Request status changed to: " + status.name());
            ctx.redirect("/manager/get_pending_reimbursement_requests/");
        }
        else{
            ctx.status(400);
            ctx.result("Could not change request status");
        }

    };


    public Handler uploadReceiptHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));

        byte[] bytes = ctx.bodyAsBytes();  // ctx.body().getBytes();

        ReimbursementRequest reimbursementRequest = Driver.reimbursementRequestService.getReimbursementRequestById(id);
        ReimbursementRequest updatedReimbursementRequest = Driver.reimbursementRequestService.appendReceiptToReimbursementRequest(reimbursementRequest, bytes);

        bytes = null;
        bytes = reimbursementRequest.getReceiptImage();

        String encodedFile = bytesToString(bytes);


        String html = "<html><h2>Receipt for request #" + id + " </h2><br><img src=\"data:image/jpeg;base64," + encodedFile + "\"></html>";
        ctx.result(html);


    };


    public Handler createReimbursementRequestHandler = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        ReimbursementRequest reimbursementRequest = gson.fromJson(json, ReimbursementRequest.class);
        ReimbursementRequest registeredReimbursementRequest = Driver.reimbursementRequestService.createReimbursementRequest(reimbursementRequest);
        String reimbursementRequestJson = gson.toJson(registeredReimbursementRequest);
        ctx.status(201);
        ctx.result(reimbursementRequestJson);
    };

    public Handler getReimbursementRequestByIdHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        ReimbursementRequest reimbursementRequest = Driver.reimbursementRequestService.getReimbursementRequestById(id);
        Gson gson = new Gson();
        String json = gson.toJson(reimbursementRequest);
        ctx.result(json);
    };

    public Handler updateReimbursementRequestHandler = (ctx) ->{
        String reimbursementRequestJSON = ctx.body();
        Gson gson = new Gson();
        ReimbursementRequest reimbursementRequest = gson.fromJson(reimbursementRequestJSON, ReimbursementRequest.class);
        ReimbursementRequest updatedReimbursementRequest = Driver.reimbursementRequestService.updateReimbursementRequest(reimbursementRequest);
        String json = gson.toJson(updatedReimbursementRequest);
        ctx.result(json);
    };



    public Handler deleteReimbursementRequestHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean result = Driver.reimbursementRequestService.deleteReimbursementRequestById(id);
        if(result){
            ctx.status(204);
        }
        else{
            ctx.status(400);
            ctx.result("Could not process your delete request");
        }
    };



}
