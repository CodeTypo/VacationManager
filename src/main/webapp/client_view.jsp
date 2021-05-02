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
                        <a class="navbar-brand" href="index.jsp">Log out</a>
                    </div>
                </div>
            </div>
        </nav>

        <div class="row form-group"></div>
        <div class="row form-group"></div>
        <div class="row form-group"></div>
        <div class="row form-group"></div>


        <c:forEach var="detail" items="${USER_DETAILS}">
                <h1>${detail.firstName}  ${detail.lastName}</h1>
        </c:forEach>

        <h2>Vacations requested by You: </h2>


        <div class="row form-group"></div>
        <div class="row form-group"></div>
        <div class="row form-group"></div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Begin date</th>
                    <th scope="col">End date</th>
                    <th scope="col">approved</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="vacation" items="${USER_VACATIONS}">
                    <tr>
                        <th scope="row">${vacation.id}</th>
                        <td>${vacation.beginDate}</td>
                        <td>${vacation.endDate}</td>
                        <td>${vacation.approved}</td>
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
