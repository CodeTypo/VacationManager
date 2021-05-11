package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Models.DetailedVacation;
import com.codetypo.VacationManager.Utilities.DbUtilAdmin;

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
import java.util.List;

/**
 * This class represents the admin functionality of approving,
 * denying and deleting vacations taken by the employee.
 */
@WebServlet("/VacationRequestManagerServlet")
public class VacationRequestManagerServlet extends HttpServlet {

    /**
     * This private field represents <code>DbUtilAdmin</code> class.
     */
    private DbUtilAdmin dbUtil;

    /**
     * This private field represents <code>DataSource</code> class.
     */
    private DataSource dataSource;

    /**
     * This is no arguments constructor.
     */
    public VacationRequestManagerServlet() {
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
     * This is an override methods, that initializes <code>DbUtilAdmin</code> class.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dbUtil = new DbUtilAdmin(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * This method is called, whenever admin wants to approve, deny or delete a vacations
     * taken by employees.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        response.setContentType("text/html");

        try {
            String command = request.getParameter("command");
            if (command == null)
                command = "LIST";
            int id;
            switch (command) {

                case "LIST":
                    listVacations(request, response);
                    break;

                case "APPROVE":
                    id = Integer.parseInt(request.getParameter("vacationID"));
                    approveVacation(request, response, id);
                    listVacations(request, response);
                    break;

                case "DENY":
                    id = Integer.parseInt(request.getParameter("vacationID"));
                    denyVacation(request, id);
                    listVacations(request, response);
                    break;

                case "DELETE":
                    id = Integer.parseInt(request.getParameter("vacationID"));
                    deleteVacation(request, response, id);
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
     * This method is called when admin wants to approve a vacation taken by the employee.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     * @param id       represents id of vacation.
     * @throws SQLException when <code>DbUtilAdmin</code> has a trouble executing SQL requests.
     */
    private void approveVacation(HttpServletRequest request, HttpServletResponse response, int id) throws SQLException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");
        dbUtil.setName(name);
        dbUtil.setPassword(password);

        dbUtil.approveVacation(id);
    }

    /**
     * This method is called when admin wants to delete a vacation taken by the employee.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     * @param id       represents id of vacation.
     * @throws SQLException when <code>DbUtilAdmin</code> has a trouble executing SQL requests.
     */
    private void deleteVacation(HttpServletRequest request, HttpServletResponse response, int id) throws SQLException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        dbUtil.setName(name);
        dbUtil.setPassword(password);
        dbUtil.deleteVacation(id);
    }

    /**
     * This method is called when admin wants to deny a vacation taken by the employee.
     *
     * @param request represents <code>HttpServletRequest</code> class.
     * @param id      represents id of vacation.
     * @throws SQLException when <code>DbUtilAdmin</code> has a trouble executing SQL requests.
     */
    private void denyVacation(HttpServletRequest request, int id) throws SQLException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        dbUtil.setName(name);
        dbUtil.setPassword(password);
        dbUtil.denyVacation(id);
    }

    /**
     * This method is always called after an approval, rejection,
     * or deletion of vacations taken by the employee is performed.
     *
     * @param request  represents <code>HttpServletRequest</code> class.
     * @param response represents <code>HttpServletResponse</code> class.
     * @throws SQLException when <code>DbUtilAdmin</code> has a trouble executing SQL requests.
     * @throws IOException  when an input or output exception is thrown.
     */
    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        dbUtil.setName(name);
        dbUtil.setPassword(password);
        List<DetailedVacation> vacationList = null;
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_request_manager.jsp");


        try {
            vacationList = dbUtil.getDetailedVacations();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("VACATIONS_LIST", vacationList);
        dispatcher.forward(request, response);
    }
}
