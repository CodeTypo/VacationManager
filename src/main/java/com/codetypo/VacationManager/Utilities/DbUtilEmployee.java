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

    /**
     * This private field represents <code>DataSource</code> class.
     */
    private DataSource dataSource;

    /**
     * This private field represents name of employee.
     */
    private String name;

    /**
     * This class represents password of employee.
     */
    private String password;

    /**
     * This is a constructor with one parameter.
     *
     * @param dataSource represents <code>DataSource</code> class.
     */
    public DbUtilEmployee(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * This is a setter of name.
     *
     * @param name represents name of employee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is a setter of password.
     *
     * @param password represents password of employee.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method returns a list of vacations, that employee taken or wants to take.
     *
     * @param empId represents id of employee.
     * @return a list of vacations.
     * @throws SQLException when has a trouble executing SQL requests.
     */
    public List<Vacation> getVacations(int empId) throws SQLException {

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

    /**
     * This method is used to log in by employee.
     *
     * @param login    represents login of employee.
     * @param password represents password of employee.
     * @return value that informs whether the employee has logged in successfully.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method returns detail.
     *
     * @param empId represents id of employee.
     * @return details.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method sets vacation that the employee wants to take.
     *
     * @param beginDate  represents begin date of vacations.
     * @param endDate    represents end date of vacations.
     * @param employeeId represents id of employee.
     * @return always true.
     * @throws SQLException when has a trouble executing SQL requests.
     */
    public boolean setVacation(LocalDate beginDate, LocalDate endDate, int employeeId) throws SQLException {

        int vacationDays = (int) ChronoUnit.DAYS.between(beginDate, endDate) + 1;

        if (isDateOK(employeeId, beginDate, endDate)) {
            updateVacationsAndDetails(employeeId, vacationDays, beginDate, endDate);
        } else {
            System.out.println("You cannot have a vacation in these days!");
        }

        return true;
    }

    /**
     * This method changes dates of vacation.
     *
     * @param vacationID represents id of vacation.
     * @param beginDate  represents begin date of vacation, that the employee want to have after change.
     * @param endDate    represents end date of vacation, that the employee want to have after change.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method checks date that the user wants to set.
     *
     * @param employeeID represents id of employee.
     * @param beginDate  represents begin date of vacation, that the employee want to have after change.
     * @param endDate    represents end date of vacation, that the employee want to have after change.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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
                int fifthCondition = (int) ChronoUnit.DAYS.between(beginDate, endDate);

                if (fifthCondition >= 0 && ((firstCondition < 0 & secondCondition < 0 & fourthCondition < 0 & thirdCondition < 0) ||
                        (secondCondition > 0 & secondCondition > 0 & fourthCondition > 0 & thirdCondition > 0) ||
                        (firstCondition == 0 & fourthCondition != 0) || fourthCondition == 0 & firstCondition != 0)) {
                    isDateOK = true;
                } else {
                    return false;
                }
            }
        }
        return isDateOK;
    }

    /**
     * This method calculates vacation days, that the employee can take .
     *
     * @param employeeID represents id of employee.
     * @return vacation days.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method updates vacation detail.
     *
     * @param employeeId   represents id of employee.
     * @param vacationDays represents vacation days.
     * @param firstDay     represents first day of vacation.
     * @param lastDay      represents last day of vacation.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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
