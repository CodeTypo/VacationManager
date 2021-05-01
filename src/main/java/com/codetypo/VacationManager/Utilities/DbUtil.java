package com.codetypo.VacationManager.Utilities;

import com.codetypo.VacationManager.Models.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public abstract class DbUtil {

    abstract List<Employee> getEmployees() throws Exception;

    protected static void close(Connection conn, Statement statement, ResultSet resultSet) {

        try {
            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (conn != null)
                conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
