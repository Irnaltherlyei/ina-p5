<%@ page import="ina.p5.Helper.LoginHelper" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>INA Praktikum 5</title>
</head>
<%
    LoginHelper loginHelper = (LoginHelper) request.getSession().getAttribute("loginHelper");

    if(loginHelper != null && loginHelper.getUser() != null){
        // User is logged in
        response.sendRedirect("input.jsp");
    }

    String loginStatusMessage = (String)session.getAttribute("loginStatusMessage");
%>
<body>
    <div id="container">
        <h1>INA Praktikum 5</h1>
        <label>Login</label>
        <form action="LoginServlet" method="get">
            <label>
                <input type="text" name="username" placeholder="username">
            </label>
            <label>
                <input type="password" name="password" placeholder="password">
            </label>
            <button type="submit">Login or Register!</button>
            <label>
                <%= loginStatusMessage == null ? "" : loginStatusMessage %>
            </label>
        </form>
    </div>
</body>
</html>