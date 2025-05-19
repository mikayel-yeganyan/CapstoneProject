<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
%>
<html>
<head><title>Admin Dashboard</title></head>
<body>
    <h2>Welcome, ${sessionScope.adminUser}! </h2>
    <form action="${pageContext.request.contextPath}/form-resources" method="GET">
        <button type="submit">See and approve new Google Form entries</button>
    </form>
    <form action="${pageContext.request.contextPath}/existing-resources" method="GET">
        <button type="submit">See and remove existing entries</button>
    </form>

    <c:if test="${not empty requestScope.msg}">
        <span>${requestScope.msg}</span>
    </c:if>
    <c:choose>
        <c:when test="${empty requestScope.resources}">
            <span>No resources to show, pressing the button will fetch new inputs if there are any</span>
        </c:when>
        <c:otherwise>
            <table border="1">
                <tr> <th>Title</th> <th>Developer</th> <th>Type</th> <th>Target Audience</th> <th>Region</th> <th>Language</th> <th>Domain</th> <th>Keywords</th> <th>Description</th> <th>Link</th> <th>Action</th> </tr>
                <c:forEach items="${requestScope.resources}" var="r">
                    <tr>
                        <td>${r.title}</td>
                        <td>${r.developer}</td>
                        <td>${r.type}</td>
                        <td>${r.target}</td>
                        <td>${r.region}</td>
                        <td>${r.language}</td>
                        <td>${r.domain}</td>
                        <td>${r.keywords}</td>
                        <td>${r.description}</td>
                        <td><a href="${r.url}">${r.url}</a></td>
                        <td>
                            <%--<form action="">
                                <button>Edit</button>
                            </form>--%>
                            <c:if test="${requestScope.action eq 'approve'}">
                                <form action="approve-resource">
                                    <input type="hidden" name="rowIndex" value="${r.sheetRowIndex}">
                                    <button type="submit" >Approve</button>
                                </form>
                            </c:if>
                            <c:if test="${requestScope.action eq 'remove'}">
                                <form action="remove-resource">
                                    <input type="hidden" name="resourceId" value="${r.id}">
                                    <button type="submit" >Remove</button>
                                </form>
                            </c:if>
                        </td>

                    </tr>
                </c:forEach>

            </table>
        </c:otherwise>
    </c:choose>

</body>
</html>


