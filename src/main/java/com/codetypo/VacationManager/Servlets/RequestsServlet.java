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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * This class represents a servlet for employee requests.
 */
@WebServlet("/RequestsServlet")
public class RequestsServlet extends HttpServlet {

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
    public RequestsServlet() {
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
     * This method is called when employee wants to change a date of vacations.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            String command = request.getParameter("command");

            if (command == null)
                command = "LIST";

            int id;

            switch (command) {
                case "LIST":
                    listVacations(request, response);
                    break;

                case "CHANGE":
                    id = Integer.parseInt(request.getParameter("vacationID"));
                    changeDate(request, id);
                    listVacations(request, response);
                    break;

                default:
                    listVacations(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * This method is always called after change a date of vacations taken by the employee is performed.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     * @throws IOException when an input or output exception is thrown.
     */
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

    /**
     * This method is called when employee wants to change date of vacations.
     *
     * @param request    represents <code>HttpServletRequest</code> class.
     * @param vacationID represents id of vacation.
     * @throws SQLException when <code>DbUtilAdmin</code> has a trouble executing SQL requests.
     */
    private void changeDate(HttpServletRequest request, int vacationID) throws SQLException {

        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        LocalDate beginDate = LocalDate.parse(request.getParameter("beginDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        dbUtil.changeDate(vacationID, beginDate, endDate);
    }
}
