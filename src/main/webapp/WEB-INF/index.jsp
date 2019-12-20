<%@ page import="ru.itpark.model.SearchByFileResult" %>
<%@ page import="ru.itpark.model.TaskResult" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>RFC-Searcher</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1 align="center">RFC-Searcher</h1>

            <form action="<%=request.getContextPath()%>" method="post" enctype="multipart/form-data" class="mt-3">
                <div class="custom-file">
                    <input type="file" id="file" name="file" class="custom-file-input" accept="text/plain" required>
                    <label class="custom-file-label" for="file">Choose file...</label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">Upload</button>
            </form>
            <div>
                <form action="<%=request.getContextPath()%>/search" method="get" enctype="multipart/form-data"
                      class="mt-3">
                    <div class="form-group">
                        <label for="phrase">Search phrase</label>
                        <input type="text" id="phrase" name="phrase" class="form-control" required>
                        <button type="submit" class="btn btn-primary mt-3">Search</button>
                    </div>
                </form>
                <form action="/status">
                    <button type="submit" class="btn btn-primary mt-3">Show status</button>
                </form>
            </div>
            <br>

            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">
                        <% if (request.getAttribute("itemsMap") != null) {%>
                        <%List<TaskResult> taskResultList = (List<TaskResult>) request.getAttribute("itemsMap");%>
                        <% for (TaskResult taskResult : taskResultList) { %>
                        <% for (SearchByFileResult fileResult : taskResult.getResult()) { %>
                    </h5>
                    <p class="card-text">
                    <h5 class="card-title"><%=fileResult.getFileName()%>
                    </h5>
                    <% for (String fileString : fileResult.getStringsFromFile()) { %>
                    <%=fileString%>
                    <% } %>
                    <% } %>
                    <div>
                        <a href="/?filename=<%=taskResult.getTempFileName()%>"><h3 class="btn btn-primary">
                            Download </h3></a>
                    </div>
                    <% } %>
                </div>
            </div>
            <form action="<%=request.getContextPath()%>" method="post" enctype="multipart/form-data" class="mt-3">
                <button type="submit" name="button" value="clear" class="btn btn-primary mt-3">Clear</button>
            </form>

        </div>
    </div>
</div>
</div>
<%@include file="bootstrap-scripts.jsp" %>

</body>
</html>
<% } %>
