package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Utilities.DbUtilEmployee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

/**
 * This class represents a servlet for registration.
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    /**
     * This private field represents <code>DbUtilEmployee</code> class.
     */
    private DbUtilEmployee dbUtil;

    /**
     * This private field represents <code>DataSource</code> class.
     */
    private DataSource dataSource;

    /**
     * This is no argument constructor.
     */
    public RegistrationServlet() {
        Context initCtx;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            dataSource = (DataSource)
                    envCtx.lookup("jdbc/vacationmanager");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is an override methods, that initializes <code>DbUtilEmployee</code> class.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dbUtil = new DbUtilEmployee(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * This method adds employee to database.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Connection conn;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            conn = dataSource.getConnection();

            if (dbUtil.loginToDB(request.getParameter("username"), request.getParameter("password")) < 0) {

                String sql = "INSERT INTO employees (e_employee_login,e_employee_password,e_is_admin) VALUES (?, ?, false);";
                statement = conn.prepareStatement(sql);
                statement.setString(1, request.getParameter("username"));
                statement.setString(2, request.getParameter("password"));

                statement.executeUpdate();

                String sql2 = "SELECT e_id FROM employees WHERE e_employee_login = ?;";
                statement = conn.prepareStatement(sql2);
                statement.setString(1, request.getParameter("username"));

                resultSet = statement.executeQuery();

                int employeeID = -1;

                while (resultSet.next()) {
                    employeeID = resultSet.getInt("e_id");
                }

                LocalDate userInputDate = LocalDate.parse(request.getParameter("date"));
                int yearsBetween = Period.between(userInputDate, LocalDate.now()).getYears();
                int availableDays;

                if (yearsBetween > 10)
                    availableDays = 26;
                else
                    availableDays = 20;

                String sql3 = "INSERT INTO details (d_employee_id, d_first_name, d_last_name, d_email, d_available_vacation_days) VALUES (?, ?, ?, ?, ?);";
                statement = conn.prepareStatement(sql3);
                statement.setInt(1, employeeID);
                statement.setString(2, request.getParameter("firstName"));
                statement.setString(3, request.getParameter("lastName"));
                statement.setString(4, request.getParameter("email"));
                statement.setInt(5, availableDays);

                statement.executeUpdate();
                response.sendRedirect("user_login.jsp");
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
