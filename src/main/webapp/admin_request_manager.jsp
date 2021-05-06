<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Admin panel</title>
    <link rel="stylesheet" href="https://bootswatch.com/4/lux/bootstrap.css">
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="#">Vacation Manager</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02"
            aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor02">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="user_login.jsp">Log out
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="AdminServlet">Database</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#">Manage requests</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Browse vacations</a>
            </li>
        </ul>
    </div>
</nav>

<br>
<br>
<h1>Vacation requests</h1>
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
        <th scope="col">Approve</th>
        <th scope="col">Deny</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="VACATIONS_LIST" scope="request" type="java.util.List"/>
    <c:forEach var="tmpVacation" items="${VACATIONS_LIST}">
        <c:url var="approveLink" value="VacationRequestManagerServlet">
            <c:param name="command" value="APPROVE"/>
            <c:param name="vacationID" value="${tmpVacation.id}"/>
        </c:url>

        <c:url var="denyLink" value="VacationRequestManagerServlet">
            <c:param name="command" value="DENY"/>
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
            <td><a href="${approveLink}">
                <button type="button" class="btn btn-success">Approve</button>
            </a>
            <td><a href="${denyLink}">
                <button type="button" class="btn btn-danger">Deny</button>
            </a>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>