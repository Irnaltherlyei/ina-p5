package ina.p5.Servlets;

import ina.p5.Helper.LoginHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/ina/p5/Servlets/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginHelper loginHelper;
        if (request.getSession().getAttribute("loginHelper") == null){
            loginHelper = new LoginHelper(request, response);
        }
        else {
            loginHelper = (LoginHelper) request.getSession().getAttribute("loginHelper");
        }

        loginHelper.doGet(request, response);

        // If login didn't succeed
    }
}
