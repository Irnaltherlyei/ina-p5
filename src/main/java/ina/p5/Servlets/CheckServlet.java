package ina.p5.Servlets;

import ina.p5.Helper.CheckHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "checkServlet", value = "/ina/p5/CheckServlet")
public class CheckServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CheckHelper checkHelper;
        if (request.getSession().getAttribute("checkHelper") == null){
            checkHelper = new CheckHelper(request, response);
        }
        else {
            checkHelper = (CheckHelper) request.getSession().getAttribute("checkHelper");
        }

        checkHelper.doGet(request, response);

        response.setStatus(303);
        response.setHeader("Location", "input.jsp");
        response.setHeader("Connection", "close");
    }

    public void destroy() {
    }
}
