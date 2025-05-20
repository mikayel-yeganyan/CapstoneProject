<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/15/25
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title> Admin Register </title>
</head>
<body>
<h2>Admin Register</h2>
<form action="${pageContext.request.contextPath}/admin-register" method="post">
  <label>Username:</label>
  <input type="text" name="username" required />
  <label>Password:</label>
  <input type="password" name="password" required />
  <button type="submit">Create Admin</button>
</form>
<p style="color:red;"><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage"): "" %></p>
</body>
</html>
