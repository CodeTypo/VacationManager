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
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This class represents a servlet for vacations.
 */
@WebServlet("/VacationServlet")
public class VacationServlet extends HttpServlet {

    /**
     * This private field represents <code>DbUtilEmployee</code> class.
     */
    private DbUtilEmployee dbUtil;

    /**
     * This private field represents <code>DataSource</code> class.
     */
    private DataSource dataSource;

    /**
     * This is no arguments constructor.
     */
    public VacationServlet() {
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
     * This method is called whenever the employee wants to add new vacation.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     * @throws IOException when an input or output exception is thrown.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        int id = (int) request.getSession().getAttribute("employeeId");

        try {
            dbUtil.setVacation(startDate, endDate, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.sendRedirect("UserServlet");
    }
}
