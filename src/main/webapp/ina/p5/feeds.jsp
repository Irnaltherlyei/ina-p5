<%@ page import="java.util.ArrayList" %>
<%@ page import="ina.p5.Helper.FeedHelper" %>
<%@ page import="ina.p5.Beans.FeedBean" %>
<%@ page import="ina.p5.Beans.ItemBean" %>
<%@ page import="ina.p5.Helper.LoginHelper" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  LoginHelper loginHelper = (LoginHelper) session.getAttribute("loginHelper");

  if(loginHelper == null || loginHelper.getUser() == null){
    // User is not logged in
    response.sendRedirect("index.jsp");
    return;
  }

  FeedHelper feedHelper = (FeedHelper) session.getAttribute("feedHelper");

  ArrayList<FeedBean> feeds = new ArrayList<>();
  if(feedHelper != null){
    feeds = feedHelper.getFeeds();
  }
%>
<html>
<head>
    <title>INA Feeds</title>
</head>
<body>
    <div>
      <%
        for (FeedBean feed : feeds) {
          %>
            <div>
              <label><%= feed.toString() %></label>
              <%
              for (ItemBean url : feed.getItemBeans()) {
              %>
                <ul>
                  <li><a href=<%= url.getLink() %> target="_blank"><%= url.getTitle() %></a></li>
                </ul>
              <%
              }
              %>
            </div>
          <%
        }
      %>
    </div>
</body>
</html>