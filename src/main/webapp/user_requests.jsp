<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>

    <title>My requests</title>
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

        <c:url var="changeLink" value="RequestsServlet">
            <c:param name="command" value="CHANGE"/>
            <c:param name="vacationID" value="${vacation.id}"/>
        </c:url>

        <tr>
            <th scope="row">${vacation.id}</th>
            <td>${vacation.beginDate}</td>
            <td>${vacation.endDate}</td>
            <td>${vacation.approved}</td>
            <td><a href="${changeLink}">
                <button type="button" class="btn btn-success">Change</button>
            </a>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
