<%--
  Created by IntelliJ IDEA.
  User: KwonJH
  Date: 2017-05-28
  Time: 오전 6:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    HttpSession httpSession = request.getSession();
    httpSession.setAttribute("test", "test");
    response.sendRedirect("/index.html");
%>
</body>
</html>
