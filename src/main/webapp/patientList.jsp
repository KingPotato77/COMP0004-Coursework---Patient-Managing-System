<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <a href="addPatient"><button style="margin-bottom: 20px; padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;">Add New Patient</button></a>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>
  <ul>
    <%
      List<String> patients = (List<String>) request.getAttribute("patientNames");
      List<String> ids = (List<String>) request.getAttribute("patientIds");
      if (patients != null && ids != null) {
        for (int i = 0; i < patients.size(); i++) {
          String patient = patients.get(i);
          String patientId = ids.get(i);
          if (patient != null && !patient.trim().isEmpty() && patientId != null && !patientId.trim().isEmpty()) {
    %>
    <li>
      <a href="patientDetails?id=<%=patientId%>"><%=patient%></a>
      <a href="editPatient?id=<%=patientId%>" style="margin-left: 20px; color: #2196F3; text-decoration: none; font-size: 14px;">[Edit]</a>
    </li>
    <%
          }
        }
      }
    %>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
<a href="/">Back to Home</a>
</body>
</html>
