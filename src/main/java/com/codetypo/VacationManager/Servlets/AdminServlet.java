package com.codetypo.VacationManager.Servlets;

import com.codetypo.VacationManager.Models.Employee;
import com.codetypo.VacationManager.Models.Vacation;
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

        response.setContentType("text/html");

        String name = request.getParameter("loginInput");
        String password = request.getParameter("passwordInput");
        System.out.println("login:" + name);
        System.out.println("pswd:" + password);

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");
            List<Employee> employeeList= null;
            List<Vacation> vacationList= null;

            try {
                employeeList = dbUtil.getEmployees();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                vacationList = dbUtil.getVacations();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
            request.setAttribute("EMPLOYEES_LIST", employeeList);
            request.setAttribute("VACATIONS_LIST", vacationList);

            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        try {

            // odczytanie zadania
            String command = request.getParameter("command");

            if (command == null)
                command = "LIST";

            switch (command) {

                case "LIST":
                    listEmployees(request, response);
                    break;

//                case "ADD":
//                    addPhones(request, response);
//                    break;
//
//                case "LOAD":
//                    loadPhone(request, response);
//                    break;
//
//                case "UPDATE":
//                    updatePhone(request, response);
//                    break;
//
//                case "DELETE":
//                    deletePhone(request, response);
//                    break;

                default:
                    listEmployees(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

//    private void deletePhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // odczytanie danych z formularza
//        String id = request.getParameter("phoneID");
//
//        // usuniecie telefonu z BD
//        dbUtil.deletePhone(id);
//
//        // wyslanie danych do strony z lista telefonow
//        listPhones(request, response);
//
//    }
//
//    private void updatePhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // odczytanie danych z formularza
//        int id = Integer.parseInt(request.getParameter("phoneID"));
//        String model = request.getParameter("nameInput");
//        String make = request.getParameter("makeInput");
//        double price = Double.parseDouble(request.getParameter("priceInput"));
//        double diagonal = Double.parseDouble(request.getParameter("diagonalInput"));
//        int storage = Integer.parseInt(request.getParameter("storageInput"));
//        String os = request.getParameter("osInput");
//
//        // utworzenie nowego telefonu
//        Phone phone = new Phone(id, model, make, price, diagonal, storage, os);
//
//        // uaktualnienie danych w BD
//        dbUtil.updatePhone(phone);
//
//        // wyslanie danych do strony z lista telefonow
//        listPhones(request, response);
//
//    }
//
//    private void loadPhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // odczytanie id telefonu z formularza
//        String id = request.getParameter("phoneID");
//
//        // pobranie  danych telefonu z BD
//        Phone phone = dbUtil.getPhone(id);
//
//        // przekazanie telefonu do obiektu request
//        request.setAttribute("PHONE", phone);
//
//        // wyslanie danych do formmularza JSP (update_phone_form)
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/update_phone_form.jsp");
//        dispatcher.forward(request, response);
//
//    }
//
//    private void addPhones(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // odczytanie danych z formularza
//        String model = request.getParameter("nameInput");
//        String make = request.getParameter("makeInput");
//        double price = Double.parseDouble(request.getParameter("priceInput"));
//        double diagonal = Double.parseDouble(request.getParameter("diagonalInput"));
//        int storage = Integer.parseInt(request.getParameter("storageInput"));
//        String os = request.getParameter("osInput");
//
//        // utworzenie obiektu klasy Phone
//        Phone phone = new Phone(model, make, price, diagonal, storage, os);
//
//        // dodanie nowego obiektu do BD
//        dbUtil.addPhone(phone);
//
//        // powrot do listy
//        listPhones(request, response);
//
//    }

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
