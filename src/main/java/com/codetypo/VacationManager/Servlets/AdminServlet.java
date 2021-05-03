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
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        response.setContentType("text/html");

        String name;
        String password;

        name = (String) request.getSession().getAttribute("login");
        password = (String) request.getSession().getAttribute("password");


        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");
            List<Employee> employeeList= null;
            List<DetailedVacation> vacationList= null;
            List<Details> detailsList= null;

            try {
                employeeList = dbUtil.getEmployees();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                vacationList = dbUtil.getDetailedVacations();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                detailsList = dbUtil.getEmployeeDetails();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
            request.setAttribute("USERS_LIST", employeeList);
            request.setAttribute("VACATIONS_LIST", vacationList);
            request.setAttribute("DETAILS_LIST", detailsList);

            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.include(request, response);
        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Employee> employeeList = dbUtil.getEmployees();

        // dodanie listy do obiektu zadania
        request.setAttribute("EMPLOYEES_LIST", employeeList);

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

        // przekazanie do JSP
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
