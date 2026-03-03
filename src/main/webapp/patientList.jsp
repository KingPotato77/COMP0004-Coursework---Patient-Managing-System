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
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>
  <ul>
    <%
      List<String> patients = (List<String>) request.getAttribute("patientNames");
      if (patients != null)
      {
        uk.ac.ucl.model.Model model = uk.ac.ucl.model.ModelFactory.getModel();
        for (int i = 0; i < patients.size(); i++)
        {
          String patient = patients.get(i);
          String patientId = model.getDf().getValue("ID", i);
          String href = "patientDetails?id=" + patientId;
    %>
    <li><a href="<%=href%>"><%=patient%></a>
    </li>
    <%  }
      }
    %>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
