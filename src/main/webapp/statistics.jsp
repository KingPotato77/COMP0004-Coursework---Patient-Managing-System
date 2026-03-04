<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Statistics</title>
  <style>
    .stat-container {
      margin: 20px 0;
      padding: 15px;
      border: 1px solid #ddd;
      border-radius: 5px;
      background-color: #f9f9f9;
    }
    .stat-title {
      font-weight: bold;
      font-size: 18px;
      margin-bottom: 10px;
      color: #333;
    }
    .stat-table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
    }
    .stat-table th, .stat-table td {
      padding: 10px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    .stat-table th {
      background-color: #4CAF50;
      color: white;
    }
    .stat-table tr:hover {
      background-color: #f5f5f5;
    }
    .highlight {
      background-color: #fff9c4;
      font-weight: bold;
    }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Patient Statistics & Analytics</h1>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>

  <!-- Overview Statistics -->
  <div class="stat-container">
    <div class="stat-title">Overview</div>
    <table class="stat-table">
      <tr>
        <th>Metric</th>
        <th>Count</th>
      </tr>
      <tr>
        <td>Total Patients</td>
        <td><%= request.getAttribute("totalPatients") %></td>
      </tr>
      <tr>
        <td>Living Patients</td>
        <td><%= request.getAttribute("livingPatients") %></td>
      </tr>
      <tr>
        <td>Deceased Patients</td>
        <td><%= request.getAttribute("deceasedPatients") %></td>
      </tr>
      <tr>
        <td>Average Age (Living)</td>
        <td><%= request.getAttribute("averageAge") %> years</td>
      </tr>
    </table>
  </div>

  <!-- Age Analysis -->
  <div class="stat-container">
    <div class="stat-title">Age Analysis</div>
    <%
      Map<String, String> oldestPerson = (Map<String, String>) request.getAttribute("oldestPerson");
      Map<String, String> youngestPerson = (Map<String, String>) request.getAttribute("youngestPerson");
    %>
    <table class="stat-table">
      <tr>
        <th>Category</th>
        <th>Name</th>
        <th>Age</th>
        <th>Birth Date</th>
      </tr>
      <%
        if (oldestPerson != null && !oldestPerson.isEmpty()) {
      %>
      <tr class="highlight">
        <td>Oldest Living</td>
        <td><%= oldestPerson.get("Name") %></td>
        <td><%= oldestPerson.get("Age") %></td>
        <td><%= oldestPerson.get("Birth Date") %></td>
      </tr>
      <%
        }
        if (youngestPerson != null && !youngestPerson.isEmpty()) {
      %>
      <tr class="highlight">
        <td>Youngest</td>
        <td><%= youngestPerson.get("Name") %></td>
        <td><%= youngestPerson.get("Age") %></td>
        <td><%= youngestPerson.get("Birth Date") %></td>
      </tr>
      <%
        }
      %>
    </table>
  </div>

  <!-- Geographic Distribution -->
  <div class="stat-container">
    <div class="stat-title">Geographic Distribution</div>
    <h3>By State</h3>
    <%
      Map<String, Integer> peopleByState = (Map<String, Integer>) request.getAttribute("peopleByState");
    %>
    <table class="stat-table">
      <tr>
        <th>State</th>
        <th>Count</th>
      </tr>
      <%
        if (peopleByState != null) {
          for (Map.Entry<String, Integer> entry : peopleByState.entrySet()) {
      %>
      <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
      </tr>
      <%
          }
        }
      %>
    </table>

    <h3 style="margin-top: 20px;">By City (Top 10)</h3>
    <%
      Map<String, Integer> peopleByCity = (Map<String, Integer>) request.getAttribute("peopleByCity");
      int cityCount = 0;
    %>
    <table class="stat-table">
      <tr>
        <th>City</th>
        <th>Count</th>
      </tr>
      <%
        if (peopleByCity != null) {
          for (Map.Entry<String, Integer> entry : peopleByCity.entrySet()) {
            if (cityCount >= 10) break;
      %>
      <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
      </tr>
      <%
            cityCount++;
          }
        }
      %>
    </table>
  </div>

  <!-- Demographics -->
  <div class="stat-container">
    <div class="stat-title">Demographics</div>

    <h3>Gender Distribution</h3>
    <%
      Map<String, Integer> genderDist = (Map<String, Integer>) request.getAttribute("genderDistribution");
    %>
    <table class="stat-table">
      <tr>
        <th>Gender</th>
        <th>Count</th>
      </tr>
      <%
        if (genderDist != null) {
          for (Map.Entry<String, Integer> entry : genderDist.entrySet()) {
      %>
      <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
      </tr>
      <%
          }
        }
      %>
    </table>

    <h3 style="margin-top: 20px;">Race Distribution</h3>
    <%
      Map<String, Integer> raceDist = (Map<String, Integer>) request.getAttribute("raceDistribution");
    %>
    <table class="stat-table">
      <tr>
        <th>Race</th>
        <th>Count</th>
      </tr>
      <%
        if (raceDist != null) {
          for (Map.Entry<String, Integer> entry : raceDist.entrySet()) {
      %>
      <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
      </tr>
      <%
          }
        }
      %>
    </table>

    <h3 style="margin-top: 20px;">Ethnicity Distribution</h3>
    <%
      Map<String, Integer> ethnicityDist = (Map<String, Integer>) request.getAttribute("ethnicityDistribution");
    %>
    <table class="stat-table">
      <tr>
        <th>Ethnicity</th>
        <th>Count</th>
      </tr>
      <%
        if (ethnicityDist != null) {
          for (Map.Entry<String, Integer> entry : ethnicityDist.entrySet()) {
      %>
      <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
      </tr>
      <%
          }
        }
      %>
    </table>

    <h3 style="margin-top: 20px;">Marital Status Distribution</h3>
    <%
      Map<String, Integer> maritalDist = (Map<String, Integer>) request.getAttribute("maritalStatusDistribution");
    %>
    <table class="stat-table">
      <tr>
        <th>Marital Status</th>
        <th>Count</th>
      </tr>
      <%
        if (maritalDist != null) {
          for (Map.Entry<String, Integer> entry : maritalDist.entrySet()) {
      %>
      <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
      </tr>
      <%
          }
        }
      %>
    </table>
  </div>

</div>
<jsp:include page="/footer.jsp"/>
<a href="/">Back to Home</a>
</body>
</html>

