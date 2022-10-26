package dev.hammet.daotests;

import dev.hammet.entities.ReimbursementRequest;
import dev.hammet.repositories.ReimbursementRequestDAO;
import dev.hammet.repositories.ReimbursementRequestDAOPostgres;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ReimbursementRequestDAOTests {

    static ReimbursementRequestDAO reimbursementRequestDAO = new ReimbursementRequestDAOPostgres();

    @Test
    @Order(1)
    void create_request_test() {
        ReimbursementRequest newRequest = new ReimbursementRequest(0, 1, 250.00f, "chairs", ReimbursementRequest.Type.OTHER);
        ReimbursementRequest savedRequest = reimbursementRequestDAO.createReimbursementRequest(newRequest);
        System.out.println(savedRequest);
        Assertions.assertNotNull(savedRequest);
    }

    @Test
    @Order(2)
    void get_request_by_id_test() {
        ReimbursementRequest gottenRequest = reimbursementRequestDAO.getReimbursementRequestById(1);
        System.out.println(gottenRequest);
        Assertions.assertNotNull(gottenRequest);
    }

    @Test
    @Order(3)
    void get_all_reimbursement_requests_test(){
        List<ReimbursementRequest> requestList =  reimbursementRequestDAO.getAllReimbursementRequests();
        for (ReimbursementRequest r : requestList)
            System.out.println(r);
        Assertions.assertTrue((requestList.size() > 1));
    }

    @Test
    @Order(4)
    void get_requests_for_employee_test() {
        List<ReimbursementRequest> requestList =  reimbursementRequestDAO.getReimbursementRequestsForEmployee(6);
        for (ReimbursementRequest r : requestList)
            System.out.println(r);
        Assertions.assertTrue((requestList.size() > 0));
    }

    @Test
    @Order(5)
    void update_request_test() {
        ReimbursementRequest request = reimbursementRequestDAO.getReimbursementRequestById(1);
        request.setDescription("updated descr");
        ReimbursementRequest updatedRequest = reimbursementRequestDAO.updateReimbursementRequest(request);
        System.out.println(updatedRequest);
        Assertions.assertEquals("updated descr", updatedRequest.getDescription());
    }

    @Test
    @Order(6)
    void delete_request_test(){
        boolean returnValue = reimbursementRequestDAO.deleteReimbursementRequestById(2);
        System.out.println(returnValue);
        Assertions.assertTrue(returnValue);
    }

    @Test
    @Order(7)
    void change_request_status_test(){
        int requestId = 4;
        ReimbursementRequest request = reimbursementRequestDAO.getReimbursementRequestById(requestId);
        if (request.getStatus() != ReimbursementRequest.Status.PENDING) {
            System.out.println("MUST BE PENDING TO CHANGE");
        } else {
            boolean returnValue = reimbursementRequestDAO.changeReimbursementRequestStatus(requestId, ReimbursementRequest.Status.APPROVED);
            System.out.println(returnValue);
            Assertions.assertTrue(returnValue);
        }

    }

}
