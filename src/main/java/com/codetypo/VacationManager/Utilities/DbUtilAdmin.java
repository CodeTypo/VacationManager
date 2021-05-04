package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.DetailedVacation;
import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Employee;
import com.codetypo.VacationManager.Models.Vacation;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class DbUtilAdmin extends DbUtil {
    
    private String name;
    private String password;
    private DataSource dataSource;

    public DbUtilAdmin(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int loginToDB(String login, String password) throws SQLException {

        int connected = -1;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM employees WHERE e_employee_login = ? AND e_employee_password = ? AND e_is_admin = 1;";
            statement = conn.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("e_id");
                connected = id;
            }

        } finally {
            close(conn, statement, resultSet);
        }
        return connected;
    }


    public List<Employee> getEmployees() throws Exception {

        List<Employee> employees = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM employees";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int id = resultSet.getInt("e_id");
                String login = resultSet.getString("e_employee_login");
                String password = resultSet.getString("e_employee_password");
                boolean isEmployee = resultSet.getBoolean("e_is_admin");

                employees.add(new Employee(id, login, password, isEmployee));
            }

        } finally {
            close(conn, statement, resultSet);
        }

        return employees;
    }

    public List<Vacation> getVacations() throws Exception {
        List<Vacation> vacations = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM vacations";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("v_id");
                Date beginDate = resultSet.getDate("v_begin_date");
                Date endDate = resultSet.getDate("v_end_date");
                boolean approved = resultSet.getBoolean("v_approved");
                vacations.add(new Vacation(id, beginDate, endDate, approved));
            }

        } finally {
            close(conn, statement, resultSet);
        }
        return vacations;
    }

    public Employee getEmployee(String id) throws Exception {

        Employee employee;

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            int adminID = Integer.parseInt(id);

            conn = dataSource.getConnection();

            String sql = "SELECT * FROM employees WHERE id = ?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, adminID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String login = resultSet.getString("e_employee_login");
                String password = resultSet.getString("e_employee_password");
                boolean isEmployee = resultSet.getBoolean("e_employee_role");

                employee = new Employee(adminID, login, password, isEmployee);

            } else {
                throw new Exception("Could not find admin with id " + adminID);
            }
            return employee;

        } finally {
            close(conn, statement, resultSet);
        }
    }

    public List<DetailedVacation> getDetailedVacations() throws Exception {
        List<DetailedVacation> vacations = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT v_id, v_employee_id,  d_first_name, d_last_name, d_email, v_begin_date, v_end_date, v_approved FROM vacations v  JOIN details d ON v.v_employee_id = d.d_employee_id;";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("v_id");
                int employeeId = resultSet.getInt("v_employee_id");
                String firstName = resultSet.getString("d_first_name");
                String lastName = resultSet.getString("d_last_name");
                String email = resultSet.getString("d_email");
                int vacationId = resultSet.getInt("v_id");
                Date beginDate = resultSet.getDate("v_begin_date");
                Date endDate = resultSet.getDate("v_end_date");
                boolean approved = resultSet.getBoolean("v_approved");

                vacations.add(new DetailedVacation(id, employeeId, firstName, lastName, email, vacationId, beginDate, endDate, approved));
            }

        } finally {
            close(conn, statement, resultSet);
        }
        return vacations;
    }

    public List<Details> getEmployeeDetails() throws SQLException {
        List<Details> details = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT d_id, d_first_name, d_Last_name, d_email, d_available_vacation_days FROM details;";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int id = resultSet.getInt("d_id");
                String firstName = resultSet.getString("d_first_name");
                String lastName = resultSet.getString("d_last_name");
                String email = resultSet.getString("d_email");
                int vacationDaysLeft = resultSet.getInt("d_available_vacation_days");
                details.add(new Details(id, firstName, lastName, email, vacationDaysLeft));
            }

        } finally {
            close(conn, statement, resultSet);
        }
        return details;
    }

    public boolean approveVacation(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();
            String sql = "UPDATE vacations SET v_approved = true WHERE v_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            close(conn, statement, resultSet);
        }
        return true;
    }

    public boolean deleteVacation(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            updateDetails(id);

            conn = dataSource.getConnection();
            String sql = "DELETE FROM vacations WHERE v_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            close(conn, statement, resultSet);
        }
        return true;
    }

    private void updateDetails(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();
            int vacationDays = 0;

            String sql = "SELECT v_id, v_employee_id , v_begin_date, v_end_date, v_approved FROM vacations WHERE v_id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            int employeeId = 0;

            while (resultSet.next()) {
                employeeId = resultSet.getInt("v_employee_id");
                Date beginDate = resultSet.getDate("v_begin_date");
                Date endDate = resultSet.getDate("v_end_date");

                vacationDays = Period.between(LocalDate.parse(beginDate.toString()), LocalDate.parse(endDate.toString())).getDays() + 1;
            }

            String sql3 = "UPDATE details SET d_available_vacation_days = d_available_vacation_days + ? WHERE d_employee_id = ?;";
            statement = conn.prepareStatement(sql3);
            statement.setInt(1, vacationDays);
            statement.setInt(2, employeeId);

            statement.executeUpdate();

        } finally {
            close(conn, statement, resultSet);
        }
    }
}