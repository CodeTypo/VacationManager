package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtilAdmin extends DbUtil {

    private String URL;
    private String name;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DbUtilAdmin(String URL) {
        this.URL = URL;
    }

    @Override
    public List<Employee> getEmployees() throws Exception {

        List<Employee> employees = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection(URL, name, password);
            String sql = "SELECT * FROM employees";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String login = resultSet.getString("employee_login");
                String password = resultSet.getString("employee_password");
                boolean isEmployee = resultSet.getBoolean("is_admin");

                employees.add(new Employee(id, login, password, isEmployee));

            }

        } finally {
            close(conn, statement, resultSet);
        }
        return employees;
    }

    public Employee getEmployee(String id) throws Exception {

        Employee employee;

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            int adminID = Integer.parseInt(id);

            conn = DriverManager.getConnection(URL, name, password);

            String sql = "SELECT * FROM employees WHERE id = ?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, adminID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String login = resultSet.getString("employee_login");
                String password = resultSet.getString("employee_password");
                boolean isEmployee = resultSet.getBoolean("employee_role");

                employee = new Employee(adminID, login, password, isEmployee);

            } else {
                throw new Exception("Could not find admin with id " + adminID);
            }
            return employee;

        } finally {
            close(conn, statement, resultSet);
        }
    }
}