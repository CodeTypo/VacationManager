package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Vacation;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DbUtilEmployee extends DbUtil {

    private DataSource dataSource;
    private String name;
    private String password;

    public DbUtilEmployee(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
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

        System.out.println(vacations.size());

        return vacations;
    }

    public int loginToDB(String login, String password) throws SQLException {

        int connected = -1;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM employees WHERE e_employee_login = ? AND e_employee_password = ? AND e_is_admin = false;";
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

    public boolean setVacation(LocalDate beginDate, LocalDate endDate, int employeeId) throws SQLException {

        int vacationDays = (int) ChronoUnit.DAYS.between(beginDate, endDate) + 1;

        if (isDateOK(employeeId, beginDate, endDate)) {
            updateVacationsAndDetails(employeeId, vacationDays, beginDate, endDate);
        } else {
            System.out.println("You cannot have a vacation in these days!");
        }

        return true;
    }

    public void changeDate(int vacationID, LocalDate beginDate, LocalDate endDate) throws SQLException {

        Connection conn = dataSource.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;
        int employeeID = 0;
        Date date1 = null;
        Date date2 = null;

        String sql = "SELECT v_id, v_employee_id, v_begin_date, v_end_date, v_approved FROM vacations WHERE v_id = ?";
        statement = conn.prepareStatement(sql);
        statement.setInt(1, vacationID);

        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            employeeID = resultSet.getInt("v_employee_id");
            date1 = resultSet.getDate("v_begin_date");
            date2 = resultSet.getDate("v_end_date");
        }

        if (isDateOK(employeeID, beginDate, endDate)) {

            String sql2 = "UPDATE vacations SET v_begin_date = ? , v_end_date = ?, v_approved = ? WHERE v_id = ?;";
            statement = conn.prepareStatement(sql2);
            statement.setDate(1, Date.valueOf(beginDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setBoolean(3, false);
            statement.setInt(4, vacationID);

            statement.executeUpdate();

            int currentMargin = (int) ChronoUnit.DAYS.between(beginDate, endDate) + 1;
            int previousMargin = (int) ChronoUnit.DAYS.between(LocalDate.parse(date1.toString()), LocalDate.parse(date2.toString())) + 1;

            String sql3 = "UPDATE details SET d_available_vacation_days = ? WHERE d_employee_id = ?;";
            statement = conn.prepareStatement(sql3);
            statement.setInt(1, availableVacationDays(employeeID) + (previousMargin - currentMargin));
            statement.setInt(2, employeeID);
            statement.executeUpdate();
        }
    }

    private boolean isDateOK(int employeeID, LocalDate beginDate, LocalDate endDate) throws SQLException {

        Connection conn = dataSource.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        boolean isDateOK = false;

        int vacationDays = (int) ChronoUnit.DAYS.between(beginDate, endDate) + 1;

        if (vacationDays <= availableVacationDays(employeeID)) {
            String sql = "SELECT v_begin_date, v_end_date FROM vacations WHERE v_employee_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, employeeID);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Date bDate = resultSet.getDate("v_begin_date");
                Date eDate = resultSet.getDate("v_end_date");

                int firstCondition = (int) ChronoUnit.DAYS.between(LocalDate.parse(bDate.toString()), beginDate);
                int secondCondition = (int) ChronoUnit.DAYS.between(LocalDate.parse(eDate.toString()), beginDate);
                int thirdCondition = (int) ChronoUnit.DAYS.between(LocalDate.parse(bDate.toString()), endDate);
                int fourthCondition = (int) ChronoUnit.DAYS.between(LocalDate.parse(eDate.toString()), endDate);
                int fifthCondition = (int) ChronoUnit.DAYS.between(LocalDate.parse(eDate.toString()), beginDate);

                if((firstCondition < 0 & secondCondition < 0 & fourthCondition < 0 & thirdCondition < 0) || fifthCondition > 0) {
                    isDateOK = true;
                } else {
                    return false;
                }
            }
        }

        return isDateOK;
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

    private void updateVacationsAndDetails(int employeeId, int vacationDays, LocalDate firstDay, LocalDate lastDay) throws SQLException {
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
        statement.setDate(2, Date.valueOf(firstDay));
        statement.setDate(3, Date.valueOf(lastDay));

        statement.executeUpdate();
    }
}
