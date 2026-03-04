<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Search</h1>
  <form method="GET" action="/runsearch">
    <input type="text" name="searchstring" placeholder="Enter search here"/>
    <input type="submit" value="Search"/>
  </form>
  <br>
  <a href="/">Back to Home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

