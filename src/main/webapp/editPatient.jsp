<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Edit Patient</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Edit Patient</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p class="error-message"><%= errorMessage %></p>
  <%
    }
    String successMessage = (String) request.getAttribute("successMessage");
    if (successMessage != null) {
  %>
      <p class="success-message"><%= successMessage %></p>
  <%
    }
  %>
  <%
    Map<String, String> patientDetails = (Map<String, String>) request.getAttribute("patientDetails");
    String patientId = (String) request.getAttribute("patientId");
    if (patientDetails != null && !patientDetails.isEmpty()) {
  %>
  <form method="POST" action="/editPatient" class="form-container">
    <input type="hidden" name="patientId" value="<%= patientId %>">
    <input type="hidden" name="action" value="save">
    <%
      for (Map.Entry<String, String> entry : patientDetails.entrySet()) {
        String colName = entry.getKey();
        String value = entry.getValue() != null ? entry.getValue() : "";
        if ("ID".equals(colName)) {
    %>
    <div class="form-group">
      <label><%= colName %> (Cannot Edit)</label>
      <input type="text" value="<%= value %>" disabled>
    </div>
    <%
        } else {
    %>
    <div class="form-group">
      <label><%= colName %></label>
      <input type="text" name="<%= colName %>" value="<%= value %>">
    </div>
    <%
        }
      }
    %>
    <div class="button-group">
      <button type="submit" class="btn-green">Save Changes</button>
      <a href="patientList" class="btn-grey">Cancel</a>
    </div>
  </form>

  <div class="delete-section">
    <h3>Delete Patient</h3>
    <p>Warning: This action cannot be undone.</p>
    <form method="POST" action="/editPatient" class="inline-form">
      <input type="hidden" name="patientId" value="<%= patientId %>">
      <input type="hidden" name="action" value="delete">
      <button type="submit" class="btn-red" onclick="return confirm('Are you sure you want to delete this patient?');">Delete Patient</button>
    </form>
  </div>
  <%
    } else {
  %>
  <p>Patient not found.</p>
  <%
    }
  %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

