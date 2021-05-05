package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Models.DetailedVacation;
import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Employee;
import com.codetypo.VacationManager.Utilities.DbUtilAdmin;
import com.codetypo.VacationManager.Utilities.DbUtilEmployee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/VacationRequestManagerServlet")
public class VacationRequestManagerServlet extends HttpServlet {

    private DbUtilAdmin dbUtil;
    private DataSource dataSource;
    private final String db_url = "jdbc:mysql://localhost:3306/vacationmanager?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";

    public VacationRequestManagerServlet() {
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
            dbUtil = new DbUtilAdmin(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

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
                    approveVacation(request,response,id);
                    listVacations(request, response);
                    break;

                case "DENY":
                    id = Integer.parseInt(request.getParameter("vacationID"));
                    denyVacation(request,response,id);
                    listVacations(request, response);
                    break;

                default:
                    listVacations(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }


    }

    private void approveVacation(HttpServletRequest request, HttpServletResponse response, int id) throws SQLException, ServletException, IOException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");
        dbUtil.setName(name);
        dbUtil.setPassword(password);

        dbUtil.approveVacation(id);
    }

    private void denyVacation(HttpServletRequest request, HttpServletResponse response, int id) throws SQLException, ServletException, IOException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        dbUtil.setName(name);
        dbUtil.setPassword(password);
        dbUtil.deleteVacation(id);
    }

    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        dbUtil.setName(name);
        dbUtil.setPassword(password);
        List<DetailedVacation> vacationList= null;
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_request_manager.jsp");


        try {
            vacationList = dbUtil.getDetailedVacations();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("VACATIONS_LIST", vacationList);
        dispatcher.forward(request, response);
    }


    private boolean validate(String name, String pass) {
        boolean status = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url, name, pass);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
