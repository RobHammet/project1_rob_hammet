package dev.hammet.repositories;

import dev.hammet.entities.Employee;
import dev.hammet.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOPostgres implements EmployeeDAO {
    @Override
    public Employee createEmployee(Employee employee) {
        // try with resources. This will create our connection and end the connection when the try block is over
        // or if something fails, it will end after the catch
        try(Connection connection = ConnectionFactory.getConnection()){
            // Here is the unfun thing about JDBC, you have to write SQL statements in Java
            // I recommend putting in comments the SQL command you are trying to execute
            //INSERT INTO books VALUES (DEFAULT, 'Great Gatsby', 'F. Scott Fitts Jerald', 0);
            String sql = "insert into employees values(default, ?, ? , ?)";
            // The only thing in the sql String that isnt "just a string" are the question marks
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Parameters START at 1, they are not indexed at 0
            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2,employee.getPassword());
            preparedStatement.setBoolean(3,employee.isManager());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();//this returns the id that was created
            resultSet.next();//you need to move the cursor to the first valid record, or you will get a null response
            int generatedKey = resultSet.getInt("id");
            employee.setId(generatedKey);
            return employee;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getEmployeeById(int id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from employees where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            // The class PreparedStatement has a method called prepareStatement (no d) that takes in a string
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("password"));
            employee.setManager(rs.getBoolean("isManager"));

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from employees";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Employee> employeeList = new ArrayList<>();

            while (rs.next()) {

                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setManager(rs.getBoolean("isManager"));
                employeeList.add(employee);
            }

            return employeeList;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {


        try(Connection connection = ConnectionFactory.getConnection()){
            //UPDATE books SET title = 'It Ends with Us', author = 'Colleen Hoover' WHERE id = 2;
            String sql = "update employees set username=?, password=?, isManager=? where id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2,employee.getPassword());
            preparedStatement.setBoolean(3,employee.isManager());
            preparedStatement.setInt(4,employee.getId());

            preparedStatement.executeUpdate();

//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            resultSet.next();
//            int generatedKey = resultSet.getInt("id");
//            employee.setId(generatedKey);
            return employee;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteEmployeeById(int id) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "delete from employees where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
