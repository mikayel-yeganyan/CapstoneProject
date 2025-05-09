<%--
  Created by IntelliJ IDEA.
  User: arevikagopoian
  Date: 5/9/25
  Time: 1:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String user = request.getParameter("username");
  String pass = request.getParameter("password");

  if ("admin".equals(user) && "admin123".equals(pass)) {
    session.setAttribute("user", "admin");
    response.sendRedirect(request.getContextPath() + "/admin/admin.jsp");
    return;
  } else {
%>
<p style="color:red;">Invalid username or password.</p>
<a href="login.jsp">Try Again</a>
<%
  }
%>

