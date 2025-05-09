<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/9/25
  Time: 1:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String currentUser = (String) session.getAttribute("user");
  if (currentUser == null || !"admin".equals(currentUser)) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<html>
<head><title>Admin Dashboard</title></head>
<body>
<h2>Welcome, Admin</h2>
<p>Here is where you validate the data from the survey:</p>
<form method="post" action="logout.jsp">
  <input type="submit" value="Logout">
</form>
</body>
</html>





