<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/12/25
  Time: 10:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="am.aua.resourcehub.DAO.ResourceDAO"%>
<%@ page import="am.aua.resourcehub.DAO.ResourceDAOImpl"%>
<%@ page import="am.aua.resourcehub.model.Resource" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Resource resource = new ResourceDAOImpl().getResourceById(id);
%>
<html>
<head><title>Edit Resource</title></head>
<body>
<h2>Edit Resource</h2>
<form action="editResource" method="post">
  <input type="hidden" name="id" value="<%= resource.getId() %>"/>
  Title: <input type="text" name="title" value="<%= resource.getTitle() %>" required/><br/>
  Description: <input type="text" name="description" value="<%= resource.getDescription() %>" required/><br/>
  <input type="submit" value="Update">
  </form>
</body>
</html>