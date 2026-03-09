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
    .side-by-side {
      display: flex;
      gap: 30px;
      align-items: flex-start;
    }
    .side-by-side .stat-table {
      flex: 1;
    }
    .chart-box {
      flex-shrink: 0;
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
      <% if (oldestPerson != null && !oldestPerson.isEmpty()) { %>
      <tr>
        <td>Oldest Living</td>
        <td><%= oldestPerson.get("Name") %></td>
        <td><%= oldestPerson.get("Age") %></td>
        <td><%= oldestPerson.get("Birth Date") %></td>
      </tr>
      <% } %>
      <% if (youngestPerson != null && !youngestPerson.isEmpty()) { %>
      <tr>
        <td>Youngest</td>
        <td><%= youngestPerson.get("Name") %></td>
        <td><%= youngestPerson.get("Age") %></td>
        <td><%= youngestPerson.get("Birth Date") %></td>
      </tr>
      <% } %>
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
      <tr><th>State</th><th>Count</th></tr>
      <% if (peopleByState != null) {
           for (Map.Entry<String, Integer> entry : peopleByState.entrySet()) { %>
      <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
      <% } } %>
    </table>

    <h3 style="margin-top: 20px;">By City (Top 10)</h3>
    <%
      Map<String, Integer> peopleByCity = (Map<String, Integer>) request.getAttribute("peopleByCity");
      int cityCount = 0;
    %>
    <div class="side-by-side">
      <table class="stat-table">
        <tr><th>City</th><th>Count</th></tr>
        <% if (peopleByCity != null) {
             for (Map.Entry<String, Integer> entry : peopleByCity.entrySet()) {
               if (cityCount >= 10) break; %>
        <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
        <% cityCount++; } } %>
      </table>
      <div class="chart-box">
        <canvas id="cityChart" width="350" height="220"></canvas>
      </div>
    </div>
  </div>

  <!-- Demographics -->
  <div class="stat-container">
    <div class="stat-title">Demographics</div>

    <h3>Gender Distribution</h3>
    <%
      Map<String, Integer> genderDist = (Map<String, Integer>) request.getAttribute("genderDistribution");
    %>
    <div class="side-by-side">
      <table class="stat-table">
        <tr><th>Gender</th><th>Count</th></tr>
        <% if (genderDist != null) {
             for (Map.Entry<String, Integer> entry : genderDist.entrySet()) { %>
        <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
        <% } } %>
      </table>
      <div class="chart-box">
        <canvas id="genderChart" width="200" height="200"></canvas>
      </div>
    </div>

    <h3 style="margin-top: 20px;">Race Distribution</h3>
    <%
      Map<String, Integer> raceDist = (Map<String, Integer>) request.getAttribute("raceDistribution");
    %>
    <div class="side-by-side">
      <table class="stat-table">
        <tr><th>Race</th><th>Count</th></tr>
        <% if (raceDist != null) {
             for (Map.Entry<String, Integer> entry : raceDist.entrySet()) { %>
        <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
        <% } } %>
      </table>
      <div class="chart-box">
        <canvas id="raceChart" width="350" height="200"></canvas>
      </div>
    </div>

    <h3 style="margin-top: 20px;">Ethnicity Distribution</h3>
    <%
      Map<String, Integer> ethnicityDist = (Map<String, Integer>) request.getAttribute("ethnicityDistribution");
    %>
    <table class="stat-table">
      <tr><th>Ethnicity</th><th>Count</th></tr>
      <% if (ethnicityDist != null) {
           for (Map.Entry<String, Integer> entry : ethnicityDist.entrySet()) { %>
      <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
      <% } } %>
    </table>

    <h3 style="margin-top: 20px;">Marital Status Distribution</h3>
    <%
      Map<String, Integer> maritalDist = (Map<String, Integer>) request.getAttribute("maritalStatusDistribution");
    %>
    <div class="side-by-side">
      <table class="stat-table">
        <tr><th>Marital Status</th><th>Count</th></tr>
        <% if (maritalDist != null) {
             for (Map.Entry<String, Integer> entry : maritalDist.entrySet()) { %>
        <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
        <% } } %>
      </table>
      <div class="chart-box">
        <canvas id="maritalChart" width="200" height="200"></canvas>
      </div>
    </div>
  </div>

</div>
<jsp:include page="/footer.jsp"/>
<a href="/">Back to Home</a>

<script>
  // helper: draw a horizontal bar chart on a canvas
  function drawBarChart(canvasId, labels, values, color) {
    var canvas = document.getElementById(canvasId);
    var ctx = canvas.getContext("2d");
    var w = canvas.width, h = canvas.height;
    var padding = { top: 10, bottom: 10, left: 10, right: 10 };
    var max = Math.max.apply(null, values) || 1;
    var barHeight = (h - padding.top - padding.bottom) / labels.length;
    var labelWidth = 110;

    ctx.clearRect(0, 0, w, h);
    ctx.font = "11px sans-serif";
    ctx.fillStyle = "#333";

    for (var i = 0; i < labels.length; i++) {
      var y = padding.top + i * barHeight;
      var barW = (values[i] / max) * (w - padding.left - padding.right - labelWidth - 30);

      // label
      ctx.fillStyle = "#333";
      ctx.textBaseline = "middle";
      ctx.fillText(labels[i], padding.left, y + barHeight / 2);

      // bar
      ctx.fillStyle = color;
      ctx.fillRect(padding.left + labelWidth, y + 3, barW, barHeight - 6);

      // value
      ctx.fillStyle = "#333";
      ctx.fillText(values[i], padding.left + labelWidth + barW + 4, y + barHeight / 2);
    }
  }

  // helper: draw a pie chart on a canvas
  function drawPieChart(canvasId, labels, values, colors) {
    var canvas = document.getElementById(canvasId);
    var ctx = canvas.getContext("2d");
    var w = canvas.width, h = canvas.height;
    var cx = w / 2, cy = (h - 30) / 2;
    var radius = Math.min(cx, cy) - 5;
    var total = values.reduce(function(a, b) { return a + b; }, 0) || 1;
    var angle = -Math.PI / 2;

    for (var i = 0; i < values.length; i++) {
      var slice = (values[i] / total) * 2 * Math.PI;
      ctx.beginPath();
      ctx.moveTo(cx, cy);
      ctx.arc(cx, cy, radius, angle, angle + slice);
      ctx.closePath();
      ctx.fillStyle = colors[i % colors.length];
      ctx.fill();
      angle += slice;
    }

    // legend at bottom
    var legendX = 5, legendY = cy + radius + 10;
    ctx.font = "11px sans-serif";
    for (var j = 0; j < labels.length; j++) {
      ctx.fillStyle = colors[j % colors.length];
      ctx.fillRect(legendX, legendY, 10, 10);
      ctx.fillStyle = "#333";
      ctx.textBaseline = "top";
      ctx.fillText(labels[j], legendX + 14, legendY);
      legendX += ctx.measureText(labels[j]).width + 30;
    }
  }

  var colors = ["#4CAF50","#2196F3","#FF9800","#9C27B0","#F44336","#00BCD4"];

  // Gender pie chart
  <%
    Map<String, Integer> genderChartData = (Map<String, Integer>) request.getAttribute("genderDistribution");
  %>
  (function() {
    var labels = [], values = [];
    <% if (genderChartData != null) {
         for (Map.Entry<String, Integer> e : genderChartData.entrySet()) { %>
      labels.push("<%= e.getKey() %>"); values.push(<%= e.getValue() %>);
    <% } } %>
    drawPieChart("genderChart", labels, values, colors);
  })();

  // City bar chart
  <%
    Map<String, Integer> cityChartData = (Map<String, Integer>) request.getAttribute("peopleByCity");
    int chartCityCount = 0;
  %>
  (function() {
    var labels = [], values = [];
    <% if (cityChartData != null) {
         for (Map.Entry<String, Integer> e : cityChartData.entrySet()) {
           if (chartCityCount >= 10) break; %>
      labels.push("<%= e.getKey() %>"); values.push(<%= e.getValue() %>);
    <% chartCityCount++; } } %>
    drawBarChart("cityChart", labels, values, "#4CAF50");
  })();

  // Race bar chart
  <%
    Map<String, Integer> raceChartData = (Map<String, Integer>) request.getAttribute("raceDistribution");
  %>
  (function() {
    var labels = [], values = [];
    <% if (raceChartData != null) {
         for (Map.Entry<String, Integer> e : raceChartData.entrySet()) { %>
      labels.push("<%= e.getKey() %>"); values.push(<%= e.getValue() %>);
    <% } } %>
    drawBarChart("raceChart", labels, values, "#2196F3");
  })();

  // Marital status pie chart
  <%
    Map<String, Integer> maritalChartData = (Map<String, Integer>) request.getAttribute("maritalStatusDistribution");
  %>
  (function() {
    var labels = [], values = [];
    <% if (maritalChartData != null) {
         for (Map.Entry<String, Integer> e : maritalChartData.entrySet()) { %>
      labels.push("<%= e.getKey() %>"); values.push(<%= e.getValue() %>);
    <% } } %>
    drawPieChart("maritalChart", labels, values, colors);
  })();
</script>
</body>
</html>

