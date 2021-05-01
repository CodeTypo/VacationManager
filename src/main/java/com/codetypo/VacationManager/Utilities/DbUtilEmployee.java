package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.Employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbUtilEmployee extends DbUtil {

    private DataSource dataSource;

    public DbUtilEmployee(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Employee> getEmployees() throws Exception {

        List<Employee> employees = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = dataSource.getConnection();

            // wyrazenie SQL
            String sql = "SELECT * FROM employees";
            statement = conn.createStatement();

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id                  = resultSet.getInt("id");
                String login            = resultSet.getString("employee_login");
                String password         = resultSet.getString("employee_password");
                String role             = resultSet.getString("employee_role");
                boolean isAdmin         = false;

                if(role.equals("admin"))
                    isAdmin = true;


                // dodanie do listy nowego obiektu
                employees.add(new Employee(id, login, password, isAdmin));

            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }

        return employees;

    }
}
