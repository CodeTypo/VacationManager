<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <!-- Importing jquery cdn -->
    <script src=
                    "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js">
    </script>

    <script src=
                    "https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity=
                    "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous">
    </script>

    <script src=
                    "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity=
                    "sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous">
    </script>

    <!-- Importing icon cdn -->
    <link rel="stylesheet" href=
            "https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Importing core bootstrap cdn -->
    <link rel="stylesheet" href=
            "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity=
                  "sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <script src=
                    "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity=
                    "sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous">
    </script>

    <!-- Importing datepicker cdn -->
    <script src=
                    "https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js">
    </script>

    <%--    <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.css">--%>
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
        <th scope="col" style="text-align: center">Begin date</th>
        <th scope="col" style="text-align: center">End date</th>
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
            <form action="RequestsServlet">
                <input hidden name="command" value="CHANGE"/>
                <input hidden name="vacationID" value="${vacation.id}"/>
                <td>
                    <!-- Container class contains the date field -->
                    <div class="container" style="max-width: 250px;">
                        <div class="form-group m-1">

                            <!-- Input field along with
                                calendar icon and -->
                            <div class="input-group date">
                                <!-- Sets the calendar icon -->
                                <span class="input-group-prepend">
                    <span class="input-group-text">
                        <i class="fa fa-calendar"
                           onclick="setDatepicker(this)">
                        </i>
                    </span>
                </span>

                                <!-- Accepts the input from calendar -->
                                <input class="form-control" type="text"
                                       name="beginDate" id="beginDate" value="${vacation.beginDate}">
                            </div>
                        </div>
                    </div>

                    <!-- JavaScript to control the actions
                         of the date picker -->
                    <script type="text/javascript">
                        function setDatepicker(_this) {

                            /* Get the parent class name so we
                                can show date picker */
                            let className = $(_this).parent()
                                .parent().parent().attr('class');

                            // Remove space and add '.'
                            let removeSpace = className.replace(' ', '.');

                            // jQuery class selector
                            $("." + removeSpace).datepicker({
                                format: "yyyy-mm-dd",

                                // Positioning where the calendar is placed
                                orientation: "bottom auto",
                                // Calendar closes when cursor is
                                // clicked outside the calendar
                                autoclose: true,
                                showOnFocus: "false"
                            });
                        }
                    </script>
                </td>
                <td>
                    <!-- Container class contains the date field -->
                    <div class="container" style="max-width: 250px;">
                        <div class="form-group m-1">

                            <!-- Input field along with
                                calendar icon and -->
                            <div class="input-group date">
                                <!-- Sets the calendar icon -->
                                <span class="input-group-prepend">
                    <span class="input-group-text">
                        <i class="fa fa-calendar"
                           onclick="setDatepicker(this)">
                        </i>
                    </span>
                </span>

                                <!-- Accepts the input from calendar -->
                                <input class="form-control" type="text"
                                       name="endDate" id="endDate" value="${vacation.endDate}">
                            </div>
                        </div>
                    </div>

                    <!-- JavaScript to control the actions
                         of the date picker -->
                    <script type="text/javascript">
                        function setDatepicker(_this) {

                            /* Get the parent class name so we
                                can show date picker */
                            let className = $(_this).parent()
                                .parent().parent().attr('class');

                            // Remove space and add '.'
                            let removeSpace = className.replace(' ', '.');

                            // jQuery class selector
                            $("." + removeSpace).datepicker({
                                format: "yyyy-mm-dd",

                                // Positioning where the calendar is placed
                                orientation: "bottom auto",
                                // Calendar closes when cursor is
                                // clicked outside the calendar
                                autoclose: true,
                                showOnFocus: "false"
                            });
                        }
                    </script>
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
