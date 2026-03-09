<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Welcome to the Patient Data App</h2>
  <nav>
    <ul>
      <li><a href="patientList">View the Patient List</a></li>
      <li><a href="search">Search</a></li>
      <li><a href="statistics">View Statistics & Analytics</a></li>
      <li><a href="export">Export to CSV</a></li>
      <li><a href="saveJSON">Save to JSON</a></li>
    </ul>
  </nav>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

