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
  <a href="addPatient" class="btn-green">Add New Patient</a>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p class="error-message"><%= errorMessage %></p>
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
      <a href="editPatient?id=<%=patientId%>" class="btn-blue">[Edit]</a>
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
