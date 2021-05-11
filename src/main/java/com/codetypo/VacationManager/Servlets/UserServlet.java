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
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a servlet for users.
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

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
    public UserServlet() {
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
     * This method is called when the user wants to log in.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        String name;
        String password;

        name = (String) request.getSession().getAttribute("login");
        password = (String) request.getSession().getAttribute("password");

        try {

            int empId = dbUtil.loginToDB(name, password);

            if (empId != -1) {
                request.getSession().setAttribute("employeeId", empId);

                List<Details> details = new ArrayList<>();
                List<Vacation> vacations;

                details.add(dbUtil.getEmployeeDetails(empId));
                request.setAttribute("USER_DETAILS", details);

                vacations = dbUtil.getVacations(empId);
                request.setAttribute("USER_VACATIONS", vacations);

                dispatcher = request.getRequestDispatcher("/client_view.jsp");
            } else {
                dispatcher = request.getRequestDispatcher("/user_login.jsp");
            }
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}