<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/12/25
  Time: 9:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="true" %>
<%@ page import="java.util.List"%>
<%@ page import="am.aua.resourcehub.model.Resource"%>
<%@ page import="am.aua.resourcehub.DAO.ResourceDAOImpl"%>

<%
      String adminUser = (String) session.getAttribute("adminUser");
      if (adminUser == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
      }

      ResourceDAOImpl dao = new ResourceDAOImpl();
      List<Resource> resources = dao.getAllResources(1,1);
%>
<html>
<head><title>Admin Dashboard</title></head>
<body>
<h2>Welcome, <%= adminUser %>! </h2>
<table border="1">
    <tr><th>ID</th><th>Title</th><th>Action</th></tr>
    <% for (Resource r : resources) { %>
    <tr>
        <td><%= r.getId() %></td>
        <td><%= r.getTitle() %></td>
        <td><a href="editResource.jsp?id=<%= r.getId() %>">Edit</a></td>
    </tr>
    <% } %>
    </table>
</body>
</html>


