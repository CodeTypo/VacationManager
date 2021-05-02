package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Employee;
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
            String sql = "SELECT vacation_id, begin_date, end_date,approved FROM employee_vacation ev  JOIN employees e ON ev.employee_id = e.id  JOIN vacations v ON ev.vacation_id = v.id  WHERE employee_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            System.out.println(statement.toString());

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery();
            System.out.println(resultSet);
            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int      id         = resultSet.getInt("vacation_id");
                Date     beginDate  = resultSet.getDate("begin_date");
                Date     endDate    = resultSet.getDate("end_date");
                boolean  approved   = resultSet.getBoolean("approved");



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
            String sql = "SELECT * FROM employees WHERE employee_login = ? AND employee_password = ?;";
            statement = conn.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            System.out.println(statement.toString());

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery();

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id            = resultSet.getInt("id");
                boolean isAdmin   = resultSet.getBoolean("is_admin");

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
            String sql = "SELECT id, first_name, Last_name, email FROM details d where employee_id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery();

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id              = resultSet.getInt("id");
                String firstName    = resultSet.getString("first_name");
                String lastName     = resultSet.getString("last_name");
                String email        = resultSet.getString("email");

                System.out.println("id: " + id);
                System.out.println("name: " + firstName + " " + lastName);
                details = new Details(id, firstName, lastName,email);            }

        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return details;
    }


}
