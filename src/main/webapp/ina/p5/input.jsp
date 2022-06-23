<%@ page import="java.util.*" %>
<%@ page import="ina.p5.Helper.CheckHelper" %>
<%@ page import="ina.p5.Helper.LoginHelper" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>INA Praktikum 5</title>
</head>
<%
    // Login data with username
    LoginHelper loginHelper = (LoginHelper) session.getAttribute("loginHelper");

    if(loginHelper == null || loginHelper.getUser() == null){
        // User is not logged in
        response.sendRedirect("index.jsp");
        return;
    }
    ArrayList<String> urls;
    if((urls = loginHelper.getUrlsList()) != null);

    // Get checked urls with error messages
    ArrayList<String> statusMessages = new ArrayList<>();

    CheckHelper checkHelper = (CheckHelper) session.getAttribute("checkHelper");
    if(checkHelper != null){
        urls = checkHelper.getUrlsBean().getUrls();
        statusMessages = checkHelper.getUrlsBean().getStatusMessages();
    }

%>
<body>
<h1>INA Praktikum 5</h1>
<div>
    <label>Welcome back <%= loginHelper.getUser().getUsername() %>.</label>
    <label>Enter URLs to get their RSS Feeds.</label>
    <form id="form" method="get">
        <div id="urlContainer">
            <%
                for (int i = 0; i < urls.size(); i++) {
            %>
                    <div>
                        <label>
                            <input type="text" name=<%="url" + (i + 1)%> value=<%= (!urls.isEmpty() && urls.size() > i && urls.get(i) != null) ? urls.get(i) : "https://www.spiegel.de/" %>>
                        </label>
                        <label><%= (!statusMessages.isEmpty() && statusMessages.size() > i && statusMessages.get(i) != null) ? statusMessages.get(i) : "" %></label>
                        <button type="button" onclick="parentNode.remove()">Delete</button>
                    </div>
            <%
                }
            %>
        </div>
        <button type="submit" formaction="CheckServlet">Check URLs!</button>
        <button type="submit" formaction="FeedServlet">Show Feeds!</button>
    </form>
    <button onclick="addUrl()">Add URL!</button>
    <a href="logout.jsp">Logout!</a>
</div>
<script>
    form = document.getElementById("form");
    urlContainer =  document.getElementById("urlContainer");

    function addUrl(){
        const urlCount = urlContainer.children.length;
        urlContainer.innerHTML += urlInput(urlCount + 1);
    }

    function urlInput(id){
        return `<div>
                    <input type="text" name="url` + id + `" value="https://url` + id + `.de">
                    <button type="button" onclick="parentNode.remove()">Delete</button>
                </div>`;
    }
</script>
</body>
</html>