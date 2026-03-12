<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Add Patient</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Add New Patient</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p class="error-message"><%= errorMessage %></p>
  <%
    }
  %>
  <%
    ArrayList<String> columnNames = (ArrayList<String>) request.getAttribute("columnNames");
    if (columnNames != null) {
  %>
  <form method="POST" action="/addPatient" class="form-container">
    <%
      for (String colName : columnNames) {
        if ("ID".equals(colName)) {
    %>
    <input type="hidden" name="ID" value="">
    <%
        } else {
    %>
    <div class="form-group">
      <label><%= colName %></label>
      <input type="text" name="<%= colName %>" placeholder="Enter <%= colName %>">
    </div>
    <%
        }
      }
    %>
    <div class="button-group">
      <button type="submit" class="btn-green">Add Patient</button>
      <a href="patientList" class="btn-grey">Cancel</a>
    </div>
  </form>
  <%
    } else {
  %>
  <p>Error loading form.</p>
  <%
    }
  %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

