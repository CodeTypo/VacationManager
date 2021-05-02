package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Models.Employee;
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

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private DataSource dataSource;
    private DbUtilEmployee dbUtil;

    public UserServlet() {
        // Obtain our environment naming context
        Context initCtx = null;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            listEmployees(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Employee> phoneList = dbUtil.getEmployees();

        request.setAttribute("EMPLOYEES_LIST", phoneList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/client_view.jsp");
        dispatcher.forward(request, response);

    }
}
