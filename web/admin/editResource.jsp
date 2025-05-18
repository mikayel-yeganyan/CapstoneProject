<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/12/25
  Time: 10:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="am.aua.resourcehub.DAO.ResourceDAOImpl"%>
<%@ page import="am.aua.resourcehub.model.Resource" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    ResourceDAOImpl dao = new ResourceDAOImpl();
    Resource resource = dao.getResourceById(id);

    List<String> allKeywords = dao.getAllKeywords();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Resource</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editResource.css">
</head>
<h2>Edit Resource</h2>

<form action="editResource" method="post">
    <input type="hidden" name="id" value="<%= resource.getId() %>" />

    Type: <input type="text" name="type" value="<%= resource.getType() %>" required /><br/><br/>
    Title: <input type="text" name="title" value="<%= resource.getTitle() %>" required /><br/><br/>
    Developer: <input type="text" name="developer" value="<%= resource.getDeveloper() %>" /><br/><br/>
    Region: <input type="text" name="region" value="<%= resource.getRegion() %>" /><br/><br/>
    Language: <input type="text" name="language" value="<%= resource.getLanguage() %>" /><br/><br/>
    URL: <input type="text" name="url" value="<%= resource.getUrl() %>" /><br/><br/>

    Keywords:<br/>
    <select name="keywords" multiple>
        <% for (String kw : allKeywords) { %>
        <option value="<%= kw %>" <%= resource.getKeywords().contains(kw) ? "selected" : "" %>>
            <%= kw %>
        </option>
        <% } %>
    </select><br/><br/>

    <input type="submit" value="Update" />
</form>

</body>
</html>