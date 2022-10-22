package dev.hammet.controllers;

import com.google.gson.Gson;
import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import dev.hammet.entities.ReimbursementRequest;
import io.javalin.http.Handler;

import java.util.List;


class AmountAndDescription {
    float amount;
    String description;
}
public class ReimbursementRequestController {

    public Handler createReimbursementRequestHandler = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        ReimbursementRequest reimbursementRequest = gson.fromJson(json, ReimbursementRequest.class);
        ReimbursementRequest registeredReimbursementRequest = Driver.reimbursementRequestService.createReimbursementRequest(reimbursementRequest);
        String reimbursementRequestJson = gson.toJson(registeredReimbursementRequest);
        ctx.status(201);
        ctx.result(reimbursementRequestJson);
    };

    public Handler makeNewReimbursementRequestHandler = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        AmountAndDescription amountAndDescription = gson.fromJson(json, AmountAndDescription.class);

        ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
        reimbursementRequest.setEmployeeId(Driver.loggedInEmployee.getId());
        reimbursementRequest.setAmount(amountAndDescription.amount);
        reimbursementRequest.setDescription(amountAndDescription.description);

        ReimbursementRequest registeredReimbursementRequest = Driver.reimbursementRequestService.createReimbursementRequest(reimbursementRequest);
        String reimbursementRequestJson = gson.toJson(registeredReimbursementRequest);
        ctx.status(201);
        ctx.result(reimbursementRequestJson);
    };

    public Handler getAllReimbursementRequestsHandler = (ctx) ->{
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getAllReimbursementRequests();
        Gson gson = new Gson();
        String json = gson.toJson(reimbursementRequestList);
        ctx.result(json);
    };

    public Handler getPendingReimbursementRequestsHandler = (ctx) ->{
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getAllReimbursementRequests();
        reimbursementRequestList.removeIf(obj -> obj.getStatus() != ReimbursementRequest.Status.PENDING);

        Gson gson = new Gson();
        String json = gson.toJson(reimbursementRequestList);
        ctx.result(json);
    };
    public Handler getReimbursementRequestsForEmployeeHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getReimbursementRequestsForEmployee(id);
        Gson gson = new Gson();
        String json = gson.toJson(reimbursementRequestList);
        ctx.result(json);
    };

    public Handler getReimbursementRequestByIdHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));//This will take what value was in the {id} and turn it into an int for us to use
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



    public Handler changeReimbursementRequestStatusHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        int statusInt = Integer.parseInt(ctx.pathParam("st"));
       // int statusInt = 0;
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
        System.out.println("changeReimbursementRequestStatusHandler running");
        //ReimbursementRequest.Status status = ReimbursementRequest.Status.valueOf(ctx.pathParam("status"));
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


}
