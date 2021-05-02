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
            // polaczenie z BD
            conn = dataSource.getConnection();
            // wyrazenie SQL
            String sql = "SELECT v_id, v_begin_date, v_end_date,v_approved FROM vacations v  JOIN employees e ON v.v_employee_id = e.e_id WHERE v_employee_id =?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            System.out.println(statement.toString());

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery();
            System.out.println(resultSet);
            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int      id         = resultSet.getInt("v_id");
                Date     beginDate  = resultSet.getDate("v_begin_date");
                Date     endDate    = resultSet.getDate("v_end_date");
                boolean  approved   = resultSet.getBoolean("v_approved");



                // dodanie do listy nowego obiektu
                vacations.add(new Vacation(
                        id,
                        beginDate,
                        endDate,
                        approved
                ));

            }
        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return vacations;
    }

    public int loginToDB (String login, String password) throws SQLException {

        int connected = -1;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;



        try {

            // polaczenie z BD
            conn = dataSource.getConnection();

            // wyrazenie SQL
            String sql = "SELECT * FROM employees WHERE e_employee_login = ? AND e_employee_password = ?;";
            statement = conn.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            System.out.println(statement.toString());

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery();

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id            = resultSet.getInt("e_id");
                boolean isAdmin   = resultSet.getBoolean("e_is_admin");

                System.out.println("id: " + id);
                System.out.println("isAdmin: " + isAdmin);
                connected = id;
            }

        } finally {
            // zamkniecie obiektow JDBC
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

            // polaczenie z BD
            conn = dataSource.getConnection();

            // wyrazenie SQL
            String sql = "SELECT d_id, d_first_name, d_Last_name, d_email, d_available_vacation_days FROM details d where d_employee_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery();

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id              = resultSet.getInt("d_id");
                String firstName    = resultSet.getString("d_first_name");
                String lastName     = resultSet.getString("d_last_name");
                String email        = resultSet.getString("d_email");
                int vacationDaysLeft= resultSet.getInt("d_available_vacation_days");

                System.out.println("id: " + id);
                System.out.println("name: " + firstName + " " + lastName);
                details = new Details(id, firstName, lastName,email,vacationDaysLeft);            }

        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return details;
    }

    public boolean setVacation (int start, int end, int employeeId) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();
            // wyrazenie SQL
            String sql = "INSERT INTO vacations (v_employee_id,v_begin_date,v_end_date,v_approved) VALUES (?,?,?,false);;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, employeeId);
            statement.setInt(2, start);
            statement.setInt(3, end);
            System.out.println(statement.toString());

            // wykonanie wyrazenia SQL
            statement.executeUpdate();
            System.out.println(resultSet);
        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return true;
    }
}
