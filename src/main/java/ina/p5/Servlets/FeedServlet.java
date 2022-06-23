package ina.p5.Servlets;

import ina.p5.Helper.FeedHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FeedServlet", value = "/ina/p5/FeedServlet")
public class FeedServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FeedHelper feedHelper;
        if (request.getSession().getAttribute("feedHelper") == null){
            feedHelper = new FeedHelper(request, response);
        }
        else {
            feedHelper = (FeedHelper) request.getSession().getAttribute("feedHelper");
        }

        feedHelper.doGet(request, response);

        response.setStatus(303);
        response.setHeader("Location", "index.jsp");
        response.setHeader("Connection", "close");
    }

    public void destroy() {
    }
}
