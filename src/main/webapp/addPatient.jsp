<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Add Patient</title>
  <style>
    .add-form {
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
    .submit-btn {
      background-color: #4CAF50;
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
  <h2>Add New Patient</h2>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>

  <%
    ArrayList<String> columnNames = (ArrayList<String>) request.getAttribute("columnNames");
    if (columnNames != null) {
  %>

  <form method="POST" action="/addPatient" class="add-form">
    <%
      for (String colName : columnNames) {
        // Skip ID - it will be auto-generated
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
      <button type="submit" class="submit-btn">Add Patient</button>
      <a href="patientList"><button type="button" class="cancel-btn">Cancel</button></a>
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

