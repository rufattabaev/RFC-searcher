<%@ page import="ru.itpark.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Status of tasks</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>
<div class="container">
    <h1 align="center"><a href="/">RFC Searcher</a></h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Searching phrase</th>
            <th scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <% if (request.getAttribute("tasks") != null) {%>
        <%List<Task> taskList = (List<Task>) request.getAttribute("tasks");%>
        <% for (Task tasks : taskList) { %>
        <tr>
            <td><%=tasks.getId() %>
            </td>
            <td><%=tasks.getPhrase() %>
            </td>
            <td><%=tasks.getStatus() %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else response.getWriter().write("No tasks");%>

</div>
<%@include file="bootstrap-scripts.jsp" %>
</body>
</html>
