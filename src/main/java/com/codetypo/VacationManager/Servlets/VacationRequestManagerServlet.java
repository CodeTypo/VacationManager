package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Models.DetailedVacation;
import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Employee;
import com.codetypo.VacationManager.Utilities.DbUtilAdmin;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/VacationRequestManagerServlet")
public class VacationRequestManagerServlet extends HttpServlet {

    private DbUtilAdmin dbUtil;
    private final String db_url = "jdbc:mysql://localhost:3306/vacationmanager?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            dbUtil = new DbUtilAdmin(db_url);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

                case "DENY":
                    id = Integer.parseInt(request.getParameter("vacationID"));
                    denyVacation(request,response,id);

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
