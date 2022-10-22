package dev.hammet.services;

import dev.hammet.entities.ReimbursementRequest;

import java.util.List;

public interface ReimbursementRequestService {

    //CREATE
    ReimbursementRequest createReimbursementRequest(ReimbursementRequest reimbursementRequest);
    //READ
    ReimbursementRequest getReimbursementRequestById(int id);

    List<ReimbursementRequest> getAllReimbursementRequests();
    List<ReimbursementRequest> getReimbursementRequestsForEmployee(int id);
    //UPDATE
    ReimbursementRequest updateReimbursementRequest(ReimbursementRequest reimbursementRequest);
    //DELETE
    boolean deleteReimbursementRequestById(int id);

    boolean changeReimbursementRequestStatus(int id, ReimbursementRequest.Status status);


}
