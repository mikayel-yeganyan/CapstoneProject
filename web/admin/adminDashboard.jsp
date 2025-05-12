<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/9/25
  Time: 1:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page session="true"%>
<%@ page import="java.util.List"%>
<%@ page import="am.aua.resourcehub.model.Resource"%>
<%@ page import="am.aua.resourcehub.DAO.ResourceDAO"%>
<%@ page import="am.aua.resourcehub.DAO.ResourceDAOImpl" %>
;
<%
  String adminUser = (String) session.getAttribute("adminUser");
  if (adminUser == null) {
    response.sendRedirect("login.jsp");
    return;
  }

%>
<html>
<head><title> Admin Dashboard </title></head>
<body>
<h2> Welcome, <%= adminUser %>! </h2>
<table border="1">
  <tr><th>ID</th><th>Title</th><th>Action</th></tr>
</table>
</body>
</html>






