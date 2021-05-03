<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.println("test book vacation");
    System.out.println(request.getSession().getAttribute("login"));
%>


<html>
    <head>
        <title>Book a vacation</title>
        <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.css">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
        <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />


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
                    <li class="nav-item">
                        <a class="nav-link" href="#">My requests</a>
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
                Start Date: <input id="startDate" name="startDate" width="276" />
                End Date: <input id="endDate" name="endDate" width="276" />
                <input type="submit" class="button" value="Submit">

            </form>
        </div>
        <script>
            var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
            $('#startDate').datepicker({
                uiLibrary: 'bootstrap4',
                iconsLibrary: 'fontawesome',
                minDate: today,
                maxDate: function () {
                    return $('#endDate').val();
                }
            });
            $('#endDate').datepicker({
                uiLibrary: 'bootstrap4',
                iconsLibrary: 'fontawesome',
                minDate: function () {
                    return $('#startDate').val();
                }
            });
        </script>


    </body>
</html>
