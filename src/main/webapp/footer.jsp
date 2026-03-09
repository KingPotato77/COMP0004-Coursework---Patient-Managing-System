<div class="links">
  <p></p>
  <%
    Integer matchedCount = (Integer) request.getAttribute("matchedCount");
    if (matchedCount != null && matchedCount > 0)
    {
  %>
  <p>Patients matched: <%= matchedCount %></p>
  <%
    }
  %>
  <div class="clearBoth"></div>
</div>