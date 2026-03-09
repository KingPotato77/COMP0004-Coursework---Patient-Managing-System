<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Save JSON</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Export to JSON</h2>
  <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
  %>
    <p><%= message %></p>
  <%
    }
  %>
  <a href="/">Back to Home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

