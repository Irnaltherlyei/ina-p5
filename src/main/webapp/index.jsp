<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>INA Praktikum 5</title>
</head>
<body>
    <div id="container">
        <label>Login</label>
        <form action="ina/p5/Servlets/LoginServlet" method="get">
            <label>
                <input type="text" name="username" placeholder="username">
            </label>
            <label>
                <input type="password" name="password" placeholder="password">
            </label>
            <button type="submit">Login or Register!</button>
        </form>
    </div>
</body>
</html>