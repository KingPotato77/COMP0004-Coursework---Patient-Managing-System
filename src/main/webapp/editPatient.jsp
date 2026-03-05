<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Edit Patient</title>
  <style>
    .edit-form {
      width: 500px;
      margin: 20px 0;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }
    .form-group input {
      width: 100%;
      padding: 8px;
      box-sizing: border-box;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .button-group {
      margin-top: 20px;
    }
    .button-group button {
      padding: 10px 20px;
      margin-right: 10px;
      cursor: pointer;
      font-size: 14px;
      border: none;
      border-radius: 4px;
    }
    .save-btn {
      background-color: #4CAF50;
      color: white;
    }
    .delete-btn {
      background-color: #f44336;
      color: white;
    }
    .cancel-btn {
      background-color: #999;
      color: white;
    }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Edit Patient</h2>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
    String successMessage = (String) request.getAttribute("successMessage");
    if (successMessage != null) {
  %>
      <p style="color: green;"><%= successMessage %></p>
  <%
    }
  %>

  <%
    Map<String, String> patientDetails = (Map<String, String>) request.getAttribute("patientDetails");
    String patientId = (String) request.getAttribute("patientId");

    if (patientDetails != null && !patientDetails.isEmpty()) {
  %>

  <form method="POST" action="/editPatient" class="edit-form">
    <input type="hidden" name="patientId" value="<%= patientId %>">
    <input type="hidden" name="action" value="save">

    <%
      for (Map.Entry<String, String> entry : patientDetails.entrySet()) {
        String colName = entry.getKey();
        String value = entry.getValue() != null ? entry.getValue() : "";

        // Don't allow editing ID
        if ("ID".equals(colName)) {
    %>
    <div class="form-group">
      <label><%= colName %> (ID - Cannot Edit)</label>
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
      <button type="submit" class="save-btn">Save Changes</button>
      <a href="patientList"><button type="button" class="cancel-btn">Cancel</button></a>
    </div>
  </form>

  <div style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #ddd;">
    <h3>Delete Patient</h3>
    <p>Warning: This action cannot be undone.</p>
    <form method="POST" action="/editPatient" style="display: inline;">
      <input type="hidden" name="patientId" value="<%= patientId %>">
      <input type="hidden" name="action" value="delete">
      <button type="submit" class="delete-btn" onclick="return confirm('Are you sure you want to delete this patient?');">Delete Patient</button>
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

