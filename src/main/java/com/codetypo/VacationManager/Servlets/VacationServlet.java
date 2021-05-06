package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Utilities.DbUtilEmployee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/VacationServlet")
public class VacationServlet extends HttpServlet {

    private DataSource dataSource;
    private DbUtilEmployee dbUtil;

    public VacationServlet() {
        // Obtain our environment naming context
        Context initCtx;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            dataSource = (DataSource)
                    envCtx.lookup("jdbc/vacationmanager");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dbUtil = new DbUtilEmployee(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        int id = (int) request.getSession().getAttribute("employeeId");

        try {
            dbUtil.setVacation(startDate,endDate,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.sendRedirect("UserServlet");
    }
}
