<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.itpark.model.TaskResult" %>
<%@ page import="ru.itpark.model.SearchByFileResult" %>
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
      <h1>Catalog</h1>
      <div class="row">
          <a href="/status">To status</a>
      </div>
      <form action="<%=request.getContextPath()%>" method="post" enctype="multipart/form-data" class="mt-3">
        <div class="custom-file">
          <input type="file" id="file" name="file" class="custom-file-input" accept="text/plain" required>
          <label class="custom-file-label" for="file">Choose file...</label>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Upload</button>
      </form>

        <form action="<%=request.getContextPath()%>/search" method="get" enctype="multipart/form-data" class="mt-3">
            <div class="form-group">
                <label for="phrase">Search phrase</label>
                <input type="text" id="phrase" name="phrase" class="form-control" required>
                <button type="submit" class="btn btn-primary mt-3">Search</button>
            </div>
        </form>

        <div class="row">
            <% if (request.getAttribute("itemsMap") != null) {%>
            <%List<TaskResult> taskResultList =(List<TaskResult>) request.getAttribute("itemsMap");%>
            <% for (TaskResult taskResult : taskResultList) { %>
            <br>  <br>

            <a href="/?filename=<%=taskResult.getTempFileName()%>"><h1> <%=taskResult.getQuery()%></h1><a/>
            <br>
            <br>

            <% for (SearchByFileResult fileResult : taskResult.getResult()) { %>
            <div class="col-sm-6 mt-3">
                <div class="card">
                    <%--<img src="<%=request.getContextPath() %>/images/<%=item.getImage()%>" class="card-img-top"--%>
                         <%--alt="<%=item.getName()%>">--%>
                    <div class="card-body">
                        <h5 class="card-title"><%=fileResult.getFileName()%></h5>


                        <% for (String fileString : fileResult.getStringsFromFile()) { %>
                            <div>
                                <%=fileString%>
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>

            <% } %>
            <% } %>
            <% } %>
        </div>
        <form action="<%=request.getContextPath()%>" method="post" enctype="multipart/form-data" class="mt-3">
            <button type="submit" name="button" value="clear">Clear</button>

        </form>
    </div>
  </div>
</div>
<%@include file="bootstrap-scripts.jsp" %>

</body>
</html>
