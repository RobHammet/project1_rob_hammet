package dev.hammet.repositories;

import dev.hammet.entities.ReimbursementRequest;
import dev.hammet.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementRequestDAOPostgres implements ReimbursementRequestDAO {

    @Override
    public ReimbursementRequest createReimbursementRequest(ReimbursementRequest reimbursementRequest) {

        try(Connection connection = ConnectionFactory.getConnection()){
            // INSERT INTO reimbursement_requests VALUES (DEFAULT, 'vacay yall', '5000.29', default, 2);
            //                                                      id,    descr,amt,status,type,eid
            String sql = "insert into reimbursement_requests values (default, ?, ?, ?, ?, null, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, reimbursementRequest.getDescription());
            preparedStatement.setFloat(2,reimbursementRequest.getAmount());
            preparedStatement.setString(3, reimbursementRequest.getStatus().name());
            preparedStatement.setString(4, reimbursementRequest.getType().name());
            preparedStatement.setInt(5,reimbursementRequest.getEmployeeId());


            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int generatedKey = resultSet.getInt("r_id");
            reimbursementRequest.setId(generatedKey);
            return reimbursementRequest;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReimbursementRequest getReimbursementRequestById(int id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement_requests where r_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            // The class PreparedStatement has a method called prepareStatement (no d) that takes in a string
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
            reimbursementRequest.setId(rs.getInt("r_id"));
            reimbursementRequest.setDescription(rs.getString("r_description"));
            reimbursementRequest.setAmount(rs.getFloat("r_amount"));
            reimbursementRequest.setStatus(ReimbursementRequest.Status.valueOf(rs.getString("r_status")));
            reimbursementRequest.setEmployeeId(rs.getInt("e_id"));
            reimbursementRequest.setType(ReimbursementRequest.Type.valueOf(rs.getString("r_type")));
            reimbursementRequest.setReceiptImage(rs.getBytes("r_receipt_img"));

            return reimbursementRequest;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReimbursementRequest> getAllReimbursementRequests() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement_requests";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

            while (rs.next()) {

                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
                reimbursementRequest.setId(rs.getInt("r_id"));
                reimbursementRequest.setDescription(rs.getString("r_description"));
                reimbursementRequest.setAmount(rs.getFloat("r_amount"));
                reimbursementRequest.setStatus(ReimbursementRequest.Status.valueOf(rs.getString("r_status")));                reimbursementRequest.setEmployeeId(rs.getInt("e_id"));
                reimbursementRequest.setType(ReimbursementRequest.Type.valueOf(rs.getString("r_type")));
                reimbursementRequest.setReceiptImage(rs.getBytes("r_receipt_img"));

                reimbursementRequestList.add(reimbursementRequest);
            }

            return reimbursementRequestList;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReimbursementRequest> getAllPendingReimbursementRequests() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement_requests where r_status='PENDING'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

            while (rs.next()) {

                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
                reimbursementRequest.setId(rs.getInt("r_id"));
                reimbursementRequest.setDescription(rs.getString("r_description"));
                reimbursementRequest.setAmount(rs.getFloat("r_amount"));
                reimbursementRequest.setStatus(ReimbursementRequest.Status.valueOf(rs.getString("r_status")));                reimbursementRequest.setEmployeeId(rs.getInt("e_id"));
                reimbursementRequest.setType(ReimbursementRequest.Type.valueOf(rs.getString("r_type")));
                reimbursementRequest.setReceiptImage(rs.getBytes("r_receipt_img"));

                reimbursementRequestList.add(reimbursementRequest);
            }

            return reimbursementRequestList;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<ReimbursementRequest> getReimbursementRequestsForEmployee(int id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement_requests where e_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

            while (rs.next()) {

                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
                reimbursementRequest.setId(rs.getInt("r_id"));
                reimbursementRequest.setDescription(rs.getString("r_description"));
                reimbursementRequest.setAmount(rs.getFloat("r_amount"));
                reimbursementRequest.setStatus(ReimbursementRequest.Status.valueOf(rs.getString("r_status")));                reimbursementRequest.setEmployeeId(rs.getInt("e_id"));
                reimbursementRequest.setType(ReimbursementRequest.Type.valueOf(rs.getString("r_type")));
                reimbursementRequest.setReceiptImage(rs.getBytes("r_receipt_img"));

                reimbursementRequestList.add(reimbursementRequest);
            }

            return reimbursementRequestList;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReimbursementRequest> getReimbursementRequestsForEmployeeOfType(int id, ReimbursementRequest.Type type) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement_requests where e_id=? and r_type=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, type.name());
            ResultSet rs = ps.executeQuery();

            List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

            while (rs.next()) {

                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
                reimbursementRequest.setId(rs.getInt("r_id"));
                reimbursementRequest.setDescription(rs.getString("r_description"));
                reimbursementRequest.setAmount(rs.getFloat("r_amount"));
                reimbursementRequest.setStatus(ReimbursementRequest.Status.valueOf(rs.getString("r_status")));                reimbursementRequest.setEmployeeId(rs.getInt("e_id"));
                reimbursementRequest.setType(ReimbursementRequest.Type.valueOf(rs.getString("r_type")));
                reimbursementRequest.setReceiptImage(rs.getBytes("r_receipt_img"));

                reimbursementRequestList.add(reimbursementRequest);
            }


            return reimbursementRequestList;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReimbursementRequest> getPendingReimbursementRequestsOfType(ReimbursementRequest.Type type) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement_requests where r_type=? and r_status='PENDING'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, type.name());
            ResultSet rs = ps.executeQuery();

            List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

            while (rs.next()) {

                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
                reimbursementRequest.setId(rs.getInt("r_id"));
                reimbursementRequest.setDescription(rs.getString("r_description"));
                reimbursementRequest.setAmount(rs.getFloat("r_amount"));
                reimbursementRequest.setStatus(ReimbursementRequest.Status.valueOf(rs.getString("r_status")));                reimbursementRequest.setEmployeeId(rs.getInt("e_id"));
                reimbursementRequest.setType(ReimbursementRequest.Type.valueOf(rs.getString("r_type")));
                reimbursementRequest.setReceiptImage(rs.getBytes("r_receipt_img"));

                reimbursementRequestList.add(reimbursementRequest);
            }


            return reimbursementRequestList;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ReimbursementRequest updateReimbursementRequest(ReimbursementRequest reimbursementRequest) {

        try(Connection connection = ConnectionFactory.getConnection()){
            //UPDATE books SET title = 'It Ends with Us', author = 'Colleen Hoover' WHERE id = 2;
            String sql = "update reimbursement_requests set r_description=?, r_amount=?, r_status=?, e_id=? where r_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, reimbursementRequest.getDescription());
            preparedStatement.setFloat(2,reimbursementRequest.getAmount());
            preparedStatement.setString(3,reimbursementRequest.getStatus().name());
            preparedStatement.setInt(4,reimbursementRequest.getEmployeeId());
            preparedStatement.setInt(5, reimbursementRequest.getId());

            preparedStatement.executeUpdate();

            return reimbursementRequest;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReimbursementRequest appendReceiptToReimbursementRequest(ReimbursementRequest request, byte[] bytes) {
        System.out.println("[[appendReceiptToReimbursementRequest]] adding this much: " + bytes.length);
        try(Connection connection = ConnectionFactory.getConnection()){
            //UPDATE books SET title = 'It Ends with Us', author = 'Colleen Hoover' WHERE id = 2;
            String sql = "update reimbursement_requests set r_receipt_img=? where r_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBytes(1, bytes);
            preparedStatement.setInt(2, request.getId());

            preparedStatement.executeUpdate();

            request.setReceiptImage(bytes);
            return request;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteReimbursementRequestById(int id) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "delete from reimbursement_requests where r_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean changeReimbursementRequestStatus(int id, ReimbursementRequest.Status status) {
        // tickets.getId()   tickets.getStatus()

        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "update reimbursement_requests set r_status=? where r_id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // cannot change status of request to anything except "approved" and denied"
            // and cannot change a ticket unless its status is "pending"
            if ((status != ReimbursementRequest.Status.APPROVED &&
                status != ReimbursementRequest.Status.DENIED) ||
                    (getReimbursementRequestById(id).getStatus() != ReimbursementRequest.Status.PENDING)) {
                throw new SQLException();
            }
            // if the status change is ok, update the request's status
            preparedStatement.setString(1, status.name());
            preparedStatement.executeUpdate();

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
