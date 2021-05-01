<%--
  Created by IntelliJ IDEA.
  User: macmini2
  Date: 08/04/2020
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Panel admina</title>
</head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<link rel="stylesheet" href="css/main.css">

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">

            <div class="style padding: 25 px">
                <a class="navbar-brand" href="index.jsp">Vacation manager</a>
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
        <th scope="col">Haslo</th>
        <th scope="col">Jest Adminem?</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="tmpEmployee" items="${EMPLOYEES_LIST}">

        <%-- definiowanie linkow--%>
        <c:url var="updateLink" value="AdminServlet">
            <c:param name="command" value="LOAD"></c:param>
            <c:param name="phoneID" value="${tmpEmployee.id}"></c:param>
        </c:url>

        <c:url var="deleteLink" value="AdminServlet">
            <c:param name="command" value="DELETE"></c:param>
            <c:param name="phoneID" value="${tmpEmployee.id}"></c:param>
        </c:url>


        <tr>
            <th scope="row">${tmpEmployee.id}</th>
            <td>${tmpEmployee.login}</td>
            <td>${tmpEmployee.password}</td>
            <td>${tmpEmployee.isAdmin}</td>
            <td><a href="${updateLink}">
                <button type="button" class="btn btn-success">Zmień dane</button>
            </a>
            <a href="${deleteLink}"
               onclick="if(!(confirm('Czy na pewno chcesz usunąć ten telefon?'))) return false">
                <button type="button" class="btn btn-danger">Usuń</button>
            </a></td>
        </tr>

    </c:forEach>
    </tbody>
</table>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="col-sm-9">
    <p><a class="btn btn-primary btn-info" href="add_phone_form.jsp" role="button">Dodaj pracownika</a></p>
</div>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="row">
    <div class="container-fluid">

        <div class="col-sm-9">
            <a href="index.jsp" class="btn btn-lg btn-primary" role="button" aria-disabled="true">Wróć do strony
                głównej</a>
        </div>
    </div>
</div>