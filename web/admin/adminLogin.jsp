<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/12/25
  Time: 9:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title> Admin Login </title>
</head>
<body>
  <h2>Admin Login</h2>
  <form method="POST" action="adminLogin">
    Username: <input type="text" name="username" required/><br/>
    Password: <input type="password" name="password" required/><br/>
    <input type="submit" value="Login" />
    </form>
  <p style="color:red;"><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage"): "" %></p>
</body>
</html>
