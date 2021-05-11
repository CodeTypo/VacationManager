package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.DetailedVacation;
import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents DbUtil for admin.
 */
public class DbUtilAdmin extends DbUtil {

    /**
     * This is private field represents <code>DataSource</code> class.
     */
    private DataSource dataSource;

    /**
     * This is all arguments constructor.
     *
     * @param dataSource represents <code>DataSource</code> class.
     */
    public DbUtilAdmin(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * This is a setter of name.
     *
     * @param name represents name.
     */
    public void setName(String name) {
    }

    /**
     * This is a setter of password.
     *
     * @param password represents password.
     */
    public void setPassword(String password) {
    }

    /**
     * This method is used to log in by admin.
     *
     * @param login    represents login of admin.
     * @param password represents password of admin.
     * @return value that informs whether the administrator has logged in successfully.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method returns a list of employees.
     *
     * @return a list of employees.
     * @throws SQLException when has a trouble executing SQL requests.
     */
    public List<Employee> getEmployees() throws SQLException {

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

    /**
     * This method returns a list of vacations.
     *
     * @return a list of vacations.
     * @throws SQLException when has a trouble executing SQL requests.
     */
    public List<DetailedVacation> getDetailedVacations() throws SQLException {
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

    /**
     * This method returns a list of details about employee.
     *
     * @return a list of details about employee.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method approves vacation that employee wanted.
     *
     * @param id represents an id of vacation taken by employee.
     * @return always true.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method denies vacation that employee wanted.
     *
     * @param id represents an id of vacation taken by employee.
     * @return always true.
     * @throws SQLException when has a trouble executing SQL requests.
     */
    public boolean denyVacation(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = dataSource.getConnection();
            String sql = "UPDATE vacations SET v_approved = false WHERE v_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            close(conn, statement, resultSet);
        }
        return true;
    }

    /**
     * This method deletes vacation that employee wanted.
     *
     * @param id represents an id of vacation taken by employee.
     * @return always true.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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

    /**
     * This method updates details.
     *
     * @param id represents id of vacation taken by employee.
     * @throws SQLException when has a trouble executing SQL requests.
     */
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