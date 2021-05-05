package com.codetypo.VacationManager.Servlets;

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
import java.io.IOException;
import java.util.List;

@WebServlet("/RequestsServlet")
public class RequestsServlet extends HttpServlet {

    private DataSource dataSource;
    private DbUtilEmployee dbUtil;

    public RequestsServlet() {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            listVacations(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        dbUtil.setName(name);
        dbUtil.setPassword(password);
        List<Vacation> vacationList = null;


        try {

            int empId = dbUtil.loginToDB(name, password);
            vacationList = dbUtil.getVacations(empId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user_requests.jsp");
        request.setAttribute("REQUESTS_LIST", vacationList);
        dispatcher.forward(request, response);
    }
}
