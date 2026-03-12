<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Details</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patient Details</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p class="error-message"><%= errorMessage %></p>
  <%
    }
    Map<String, String> patientDetails = (Map<String, String>) request.getAttribute("patientDetails");
    if (patientDetails != null && !patientDetails.isEmpty()) {
  %>
  <table border="1">
    <tr>
      <th>Field</th>
      <th>Value</th>
    </tr>
    <%
      for (Map.Entry<String, String> entry : patientDetails.entrySet()) {
    %>
    <tr>
      <td><%= entry.getKey() %></td>
      <td><%= entry.getValue() %></td>
    </tr>
    <%
      }
    %>
  </table>
  <%
    }
  %>
  <br>
  <%
    String patientId = request.getParameter("id");
    if (patientId != null) {
  %>
  <a href="editPatient?id=<%= patientId %>">Edit Patient</a> |
  <%
    }
  %>
  <a href="patientList">Back to Patient List</a> |
  <a href="/search">Back to Search</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

