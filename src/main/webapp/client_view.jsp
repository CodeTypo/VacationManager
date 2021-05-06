<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    System.out.println("test client view");
    System.out.println(request.getSession().getAttribute("login"));
%>

<html>
<head>
    <title>Employee Panel</title>
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
                <a class="nav-link" href="index.jsp">Log out
                </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="RequestsServlet">My requests</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="book_vacation.jsp">Book Vacation</a>
            </li>
        </ul>
    </div>
</nav>

<br>

<jsp:useBean id="USER_DETAILS" scope="request" type="java.util.List"/>
<c:forEach var="detail" items="${USER_DETAILS}">
    <h1>${detail.firstName} ${detail.lastName}</h1>
</c:forEach>

<br>
<br>

<h2>Vacations requested by You: </h2>

<br>

<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Begin date</th>
        <th scope="col">End date</th>
        <th scope="col">approved</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="USER_VACATIONS" scope="request" type="java.util.List"/>
    <c:forEach var="vacation" items="${USER_VACATIONS}">
        <tr class="table-secondary">
            <th scope="row">${vacation.id}</th>
            <td>${vacation.beginDate}</td>
            <td>${vacation.endDate}</td>
            <td>${vacation.approved}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
