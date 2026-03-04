<div class="links">
  <p>The footer</p>
  <%
    Integer matchedCount = (Integer) request.getAttribute("matchedCount");
    if (matchedCount != null)
    {
  %>
  <p>Patients matched: <%= matchedCount %></p>
  <%
    }
  %>
  <div class="clearBoth"></div>
</div>