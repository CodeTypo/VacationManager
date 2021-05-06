<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <link rel="stylesheet" href="https://bootswatch.com/4/lux/bootstrap.css">
    <title>My requests</title>
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
                <a class="nav-link" href="UserServlet">Home</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#">My requests</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="book_vacation.jsp">Book Vacation</a>
            </li>
        </ul>
    </div>
</nav>

<br>
<br>
<br>

<h2>Vacations that you can change: </h2>

<br>

<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Begin date</th>
        <th scope="col">End date</th>
        <th scope="col">approved</th>
        <th scope="col">change date</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="REQUESTS_LIST" scope="request" type="java.util.List"/>
    <c:forEach var="vacation" items="${REQUESTS_LIST}">
        <tr>
            <th scope="row">${vacation.id}</th>
            <form action="RequestsServlet">
                <input hidden name="command" value="CHANGE"/>
                <input hidden name="vacationID" value="${vacation.id}"/>

                <td>
                    <input type="date" name="beginDate" value="${vacation.beginDate}">
                </td>
                <td>
                    <input type="date" name="endDate" value="${vacation.endDate}">
                </td>
                <td>${vacation.approved}</td>
                <td>
                    <button type="submit" class="btn btn-success">Change</button>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
