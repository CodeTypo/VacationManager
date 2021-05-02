<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
    <head>
        <title>Employees</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="css/main.css">
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <div class="style padding: 25 px">
                        <a class="navbar-brand" href="index.jsp">Vacation Manager</a>
                    </div>
                </div>
            </div>
        </nav>

        <div class="row form-group"></div>
        <div class="row form-group"></div>
        <div class="row form-group"></div>
        <div class="row form-group"></div>


        <h1>Employees</h1>

        <div class="row form-group"></div>
        <div class="row form-group"></div>
        <div class="row form-group"></div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Login</th>
                    <th scope="col">Password</th>
                    <th scope="col">isAdmin</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="tmpEmployee" items="${EMPLOYEES_LIST}">
                    <tr>
                        <th scope="row">${tmpEmployee.id}</th>
                        <td>${tmpEmployee.login}</td>
                        <td>${tmpEmployee.password}</td>
                        <td>${tmpEmployee.isAdmin}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="row form-group"></div>
        <div class="row form-group"></div>
        <div class="row form-group"></div>

        <div class="row">
            <div class="container-fluid">

                <div class="col-sm-9">
                    <a href="index.jsp" class="btn btn-lg btn-primary" role="button" aria-disabled="true">Wróć do strony głównej</a>
                </div>
            </div>
        </div>
    </body>
</html>
