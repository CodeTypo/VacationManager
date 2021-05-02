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
import java.io.IOException;
import java.util.ArrayList;
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

        String name = request.getParameter("userLogin");
        String password = request.getParameter("userPassword");
        RequestDispatcher dispatcher;

        System.out.println("login:" + name);
        System.out.println("pswd:" + password);

        try {

            int empId = dbUtil.loginToDB(name,password);

            if(empId != -1) {

                List<Details>details = new ArrayList<>();
                List<Vacation>vacations = new ArrayList<>();

                details.add(dbUtil.getEmployeeDetails(empId));
                request.setAttribute("USER_DETAILS", details);

                vacations = dbUtil.getVacations(empId);
                request.setAttribute("USER_VACATIONS", vacations);


                dispatcher = request.getRequestDispatcher("/client_view.jsp");
            } else {
                dispatcher = request.getRequestDispatcher("/index.jsp");
            }
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
