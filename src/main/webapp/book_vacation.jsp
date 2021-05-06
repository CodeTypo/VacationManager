<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.println("test book vacation");
    System.out.println(request.getSession().getAttribute("login"));
%>


<html>
<head>
    <title>Book a vacation</title>
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
            <li class="nav-item">
                <a class="nav-link" href="UserServlet">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="RequestsServlet">My requests</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#">Book Vacation</a>
            </li>
        </ul>
    </div>
</nav>

<br>
<br>
<h2>Vacation booking: </h2>
<br>

<div class="container">
    <form action="VacationServlet">
        Start Date: <input id="startDate" name="startDate" type="date" width="276"/>
        End Date: <input id="endDate" name="endDate" type="date" width="276"/>
        <input type="submit" class="button" value="Submit">
    </form>
</div>
</body>
</html>
