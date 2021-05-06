package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Models.DetailedVacation;
import com.codetypo.VacationManager.Models.Details;
import com.codetypo.VacationManager.Models.Employee;
import com.codetypo.VacationManager.Utilities.DbUtilAdmin;

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
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    private DataSource dataSource;
    private DbUtilAdmin dbUtil;

    public AdminServlet() {

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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        try {
            dbUtil = new DbUtilAdmin(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");

        RequestDispatcher dispatcher;
        String name;
        String password;

        name = (String) request.getSession().getAttribute("login");
        password = (String) request.getSession().getAttribute("password");

        try {
            int empId = dbUtil.loginToDB(name, password);

            if (empId != -1) {

                dispatcher = request.getRequestDispatcher("/admin_view.jsp");
                List<Employee> employeeList = null;
                List<DetailedVacation> vacationList = null;
                List<Details> detailsList = null;

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

                request.setAttribute("USERS_LIST", employeeList);
                request.setAttribute("VACATIONS_LIST", vacationList);
                request.setAttribute("DETAILS_LIST", detailsList);

                dispatcher.forward(request, response);

            } else {
                dispatcher = request.getRequestDispatcher("/user_login.jsp");
                dispatcher.include(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Employee> employeeList = dbUtil.getEmployees();

        request.setAttribute("EMPLOYEES_LIST", employeeList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

        dispatcher.forward(request, response);
    }
}
