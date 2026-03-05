<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Search Results</h1>
  <%
    String searchTerm = (String) request.getAttribute("searchTerm");
    if (searchTerm != null) {
  %>
      <p>Search results for: "<strong><%= searchTerm %></strong>"</p>
  <%
    }
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
    List<Map<String, String>> patients = (List<Map<String, String>>) request.getAttribute("result");
    if (patients != null && patients.size() != 0)
    {
    %>
    <ul>
      <%
        for (Map<String, String> patient : patients)
        {
          String patientId = patient.get("id");
          String patientName = patient.get("name");
          String matchedFields = patient.get("matchedFields");
      %>
      <li>
        <a href="patientDetails?id=<%= patientId %>"><%= patientName %></a>
        <span style="color: #666; font-size: 14px;"> - Matched in: <%= matchedFields %></span>
      </li>
     <% }
    } else if (errorMessage == null)
    {%>
      <p>No patients found matching your search term.</p>
  <%}%>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
<a href="/search">Back to Search</a>
</body>
</html>