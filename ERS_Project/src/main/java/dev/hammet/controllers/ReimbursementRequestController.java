package dev.hammet.controllers;

import com.google.gson.Gson;
import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import dev.hammet.entities.ReimbursementRequest;
import io.javalin.http.Handler;

import java.util.List;

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


    public Handler getAllReimbursementRequests = (ctx) ->{
        List<ReimbursementRequest> reimbursementRequestList = Driver.reimbursementRequestService.getAllReimbursementRequests();
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


}
