package dev.hammet.services;

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

        if(reimbursementRequest.getDescription().length() == 0){
            throw new RuntimeException("Description cannot be empty.");
        }
        if(reimbursementRequest.getAmount() > 1000000){
            throw new RuntimeException("Amount cannot be in excess of one million dollars.");
        }
        try {
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
    public boolean deleteReimbursementRequestById(int id) {
        return this.reimbursementRequestDAO.deleteReimbursementRequestById(id);
    }
}
