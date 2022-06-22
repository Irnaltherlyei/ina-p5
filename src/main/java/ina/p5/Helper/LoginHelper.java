package ina.p5.Helper;

import ina.p5.Beans.UserEntity;
import ina.p5.PersistenceUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginHelper extends HelperBase {
    private UserEntity user;

    public LoginHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("loginHelper") == null) {
            request.getSession().setAttribute("loginHelper", this);
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserEntity checkUser = new UserEntity();
        checkUser.setPassword(password);
        checkUser.setUsername(username);

        PersistenceUtil<UserEntity> persistenceUtil = new PersistenceUtil<UserEntity>();

        boolean userExists = persistenceUtil.doNamedQuery("User.byName", UserEntity.class, new String[]{username}).size() > 0;
        if (userExists) {
            System.out.println(username + " exists! Checking password.");
            boolean passwordMatching = persistenceUtil.doNamedQuery("User.isMatching", UserEntity.class, new String[]{username, password}).size() > 0;
            if(passwordMatching){
                System.out.println("Password for " + username + " is correct. Logged in!");
                user = checkUser;
                // Redirect
            } else {
                System.out.println("Wrong password for " + username);
            }
        } else {
            System.out.println("New user: " + username + " created.");
            persistenceUtil.save(checkUser);
        }

        persistenceUtil.close();
    }
}
