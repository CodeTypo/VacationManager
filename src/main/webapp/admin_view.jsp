<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
    <head>
        <title>Admin panel</title>
        <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.css">
    </head>

    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Vacation Manager</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02"
                aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarColor02">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Log out
                    </a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="#">Database</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="VacationRequestManagerServlet">Manage requests</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Browse vacations</a>
                </li>
            </ul>
        </div>
    </nav>

        <br>
        <br>
        <h1>Users</h1>
        <br>

        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Login</th>
                <th scope="col">Password</th>
                <th scope="col">Admin rights</th>
            </tr>
            </thead>
            <tbody>
                <jsp:useBean id="USERS_LIST" scope="request" type="java.util.List"/>
                <c:forEach var="tmpEmployee" items="${USERS_LIST}">
                    <tr class="table-secondary">
                        <th scope="row">${tmpEmployee.id}</th>
                        <td>${tmpEmployee.login}</td>
                        <td>${tmpEmployee.password}</td>
                        <td>${tmpEmployee.isAdmin}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <br>
        <br>
        <h1>Details</h1>
        <br>

        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Email</th>
                <th scope="col">Vacation days left</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="DETAILS_LIST" scope="request" type="java.util.List"/>
            <c:forEach var="tmpDetail" items="${DETAILS_LIST}">
                <tr class="table-secondary">
                    <th scope="row">${tmpDetail.id}</th>
                    <td>${tmpDetail.firstName}</td>
                    <td>${tmpDetail.lastName}</td>
                    <td>${tmpDetail.email}</td>
                    <td>${tmpDetail.vacationDaysLeft}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

            <br>
            <br>
            <h1>Vacations</h1>
            <br>


        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">emp#</th>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Email</th>
                <th scope="col">vac#</th>
                <th scope="col">Begins</th>
                <th scope="col">Ends</th>
                <th scope="col">Approved</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="VACATIONS_LIST" scope="request" type="java.util.List"/>
            <c:forEach var="tmpVacation" items="${VACATIONS_LIST}">
                <c:url var="approveLink" value="AdminServlet">
                    <c:param name="command" value="APPROVE"/>
                    <c:param name="vacationID" value="${tmpVacation.id}"/>
                </c:url>
                <tr class="table-secondary">
                    <th scope="row">${tmpVacation.id}</th>
                    <td>${tmpVacation.employeeId}</td>
                    <td>${tmpVacation.employeeFirstName}</td>
                    <td>${tmpVacation.employeeLastName}</td>
                    <td>${tmpVacation.email}</td>
                    <td>${tmpVacation.vacationId}</td>
                    <td>${tmpVacation.beginDate}</td>
                    <td>${tmpVacation.endDate}</td>
                    <td>${tmpVacation.approved}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </body>
</html>