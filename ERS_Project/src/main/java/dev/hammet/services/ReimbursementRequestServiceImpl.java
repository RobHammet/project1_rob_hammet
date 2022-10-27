package dev.hammet.services;

import dev.hammet.driver.Driver;
import dev.hammet.entities.Employee;
import dev.hammet.entities.ReimbursementRequest;
import dev.hammet.repositories.EmployeeDAO;
import dev.hammet.repositories.ReimbursementRequestDAO;

import java.sql.SQLException;
import java.util.List;

public class ReimbursementRequestServiceImpl implements ReimbursementRequestService {


    private ReimbursementRequestDAO reimbursementRequestDAO;
    public ReimbursementRequestServiceImpl(ReimbursementRequestDAO reimbursementRequestDAO){  this.reimbursementRequestDAO = reimbursementRequestDAO;  }


    @Override
    public ReimbursementRequest createReimbursementRequest(ReimbursementRequest reimbursementRequest) {

        if(reimbursementRequest.getDescription() == null || reimbursementRequest.getDescription().length() == 0){
            throw new RuntimeException("Description cannot be empty.");
        }

        if (reimbursementRequest.getAmount() > 50000 ||
            reimbursementRequest.getAmount() <= 0){
            throw new RuntimeException("Amount cannot be zero or in excess of 50000 dollars.");
        }
        try {
            reimbursementRequest.setEmployeeId(Driver.loggedInEmployee.getId());
            ReimbursementRequest savedReimbursementRequest = this.reimbursementRequestDAO.createReimbursementRequest(reimbursementRequest);

            System.out.println(savedReimbursementRequest.toString());

            return savedReimbursementRequest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ReimbursementRequest getReimbursementRequestById(int id) {
        return this.reimbursementRequestDAO.getReimbursementRequestById(id);
    }

    @Override
    public List<ReimbursementRequest> getAllReimbursementRequests() {
        return this.reimbursementRequestDAO.getAllReimbursementRequests();
    }
    @Override
    public List<ReimbursementRequest> getAllPendingReimbursementRequests() {
        return this.reimbursementRequestDAO.getAllPendingReimbursementRequests();
    }

    @Override
    public List<ReimbursementRequest> getReimbursementRequestsForEmployee(int id) {
        return this.reimbursementRequestDAO.getReimbursementRequestsForEmployee(id);
    }

    @Override
    public List<ReimbursementRequest> getReimbursementRequestsForEmployeeOfType(int id, ReimbursementRequest.Type type) {
        return this.reimbursementRequestDAO.getReimbursementRequestsForEmployeeOfType(id, type);
    }
    @Override
    public List<ReimbursementRequest> getPendingReimbursementRequestsOfType(ReimbursementRequest.Type type) {
        return this.reimbursementRequestDAO.getPendingReimbursementRequestsOfType(type);
    }

    @Override
    public ReimbursementRequest updateReimbursementRequest(ReimbursementRequest reimbursementRequest) {
        if(reimbursementRequest.getDescription().length() == 0){
            throw new RuntimeException("Description cannot be empty.");
        }
        if(reimbursementRequest.getAmount() > 1000000){
            throw new RuntimeException("Amount cannot be in excess of one million dollars.");
        }
        return this.reimbursementRequestDAO.updateReimbursementRequest(reimbursementRequest);
    }

    @Override
    public ReimbursementRequest appendReceiptToReimbursementRequest(ReimbursementRequest request, byte[] bytes) {
        if (bytes.length <= 0) {
            return null;
        } else
            return this.reimbursementRequestDAO.appendReceiptToReimbursementRequest(request, bytes);
    }


    @Override
    public boolean deleteReimbursementRequestById(int id) {
        return this.reimbursementRequestDAO.deleteReimbursementRequestById(id);
    }

    @Override
    public boolean changeReimbursementRequestStatus(int id, ReimbursementRequest.Status status) {
        return this.reimbursementRequestDAO.changeReimbursementRequestStatus(id, status);
    }
}
