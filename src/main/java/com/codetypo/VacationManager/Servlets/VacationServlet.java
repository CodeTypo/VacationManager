package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Vacation;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;

        String startString = request.getParameter("startDate");
        String endString = request.getParameter("endDate");

        String [] elementsStart = startString.split("/");
        String [] elementsEnd = endString.split("/");
        String startYear = elementsStart[2];
        String startMonth = elementsStart[0];
        String startDay = elementsStart[1];
        String endYear = elementsEnd[2];
        String endMonth = elementsEnd[0];
        String endDay = elementsEnd[1];
        startString = startYear + startMonth + startDay;
        endString = endYear + endMonth + endDay;
        int start = Integer.parseInt(startString);
        int end = Integer.parseInt(endString);

        int id = (int) request.getSession().getAttribute("employeeId");

        try {
            dbUtil.setVacation(start,end,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
