package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Vacation;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtilEmployee extends DbUtil {

    private DataSource dataSource;

    public DbUtilEmployee(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Vacation> getVacations(int empId) throws Exception {

        List<Vacation> vacations = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT v_id, v_begin_date, v_end_date,v_approved FROM vacations v  JOIN employees e ON v.v_employee_id = e.e_id WHERE v_employee_id =?;";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("v_id");
                Date beginDate = resultSet.getDate("v_begin_date");
                Date endDate = resultSet.getDate("v_end_date");
                boolean approved = resultSet.getBoolean("v_approved");

                vacations.add(new Vacation(
                        id,
                        beginDate,
                        endDate,
                        approved
                ));

            }
        } finally {
            close(conn, statement, resultSet);
        }
        return vacations;
    }

    public int loginToDB(String login, String password) throws SQLException {

        int connected = -1;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM employees WHERE e_employee_login = ? AND e_employee_password = ?;";
            statement = conn.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("e_id");
                boolean isAdmin = resultSet.getBoolean("e_is_admin");

                System.out.println("id: " + id);
                System.out.println("isAdmin: " + isAdmin);
                connected = id;
            }

        } finally {
            close(conn, statement, resultSet);
        }
        return connected;
    }

    public Details getEmployeeDetails(int empId) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Details details = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT d_id, d_first_name, d_Last_name, d_email, d_available_vacation_days FROM details d where d_employee_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("d_id");
                String firstName = resultSet.getString("d_first_name");
                String lastName = resultSet.getString("d_last_name");
                String email = resultSet.getString("d_email");
                int vacationDaysLeft = resultSet.getInt("d_available_vacation_days");
                details = new Details(id, firstName, lastName, email, vacationDaysLeft);
            }

        } finally {
            close(conn, statement, resultSet);
        }
        return details;
    }

    public boolean setVacation(int start, int end, int employeeId) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();

            int vacationDays = end - start + 1;

            if (vacationDays <= availableVacationDays(employeeId)) {

                String sql = "SELECT v_begin_date, v_end_date FROM vacations WHERE v_employee_id = ?;";
                statement = conn.prepareStatement(sql);
                statement.setInt(1, employeeId);

                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Date beginDate = resultSet.getDate("v_begin_date");
                    Date endDate = resultSet.getDate("v_end_date");
                    String[] bDate = String.valueOf(beginDate).split("-");
                    String[] eDate = String.valueOf(endDate).split("-");

                    if (start >= Integer.parseInt(bDate[0] + bDate[1] + bDate[2]) & start <= Integer.parseInt(eDate[0] + eDate[1] + eDate[2]) |
                            start + vacationDays <= Integer.parseInt(eDate[0] + eDate[1] + eDate[2])) {
                        System.out.println("You have vacation between these days!");
                    } else {
                        updateVacationsAndDetails(employeeId, vacationDays, start, end);
                    }
                }
            } else {
                System.out.println("You cannot take that many vacation days!");
            }

        } finally {
            close(conn, statement, resultSet);
        }
        return true;
    }

    private int availableVacationDays(int employeeID) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement statement;

        ResultSet resultSet;

        String sql = "SELECT d_available_vacation_days FROM details where d_employee_id = ?;";
        statement = conn.prepareStatement(sql);
        statement.setInt(1, employeeID);
        int vacationDays = 0;

        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            vacationDays = resultSet.getInt("d_available_vacation_days");
        }

        return vacationDays;
    }

    private void updateVacationsAndDetails(int employeeId, int vacationDays, int firstDay, int lastDay) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement statement;

        String sql3 = "UPDATE details SET d_available_vacation_days = ? WHERE d_employee_id = ?;";
        statement = conn.prepareStatement(sql3);
        statement.setInt(1, availableVacationDays(employeeId) - vacationDays);
        statement.setInt(2, employeeId);

        statement.executeUpdate();

        String sql4 = "INSERT INTO vacations (v_employee_id,v_begin_date,v_end_date,v_approved) VALUES (?,?,?,false);;";
        statement = conn.prepareStatement(sql4);
        statement.setInt(1, employeeId);
        statement.setInt(2, firstDay);
        statement.setInt(3, lastDay);

        statement.executeUpdate();
    }
}
