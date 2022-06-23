package ina.p5.Helper;

import ina.p5.Beans.UrlsEntity;
import ina.p5.Beans.UserEntity;
import ina.p5.PersistenceUtil;
import ina.p5.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.ArrayList;

public class LoginHelper extends HelperBase {
    private UserEntity user;
    private ArrayList<UrlsEntity> urlsList;

    public LoginHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     * Simply checks if the user exists. If not create one. If the user exists check if the given password matches.
     * For any errors displaying status messages.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("loginHelper") == null) {
            request.getSession().setAttribute("loginHelper", this);
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null){
            return;
        }

        UserEntity checkUser = new UserEntity();
        checkUser.setPassword(password);
        checkUser.setUsername(username);

        ValidationUtil<UserEntity> validationUtil = new ValidationUtil<>();
        validationUtil.isValid(checkUser);

        for (ConstraintViolation<UserEntity> violation :
                validationUtil.getViolations()) {
            request.getSession().setAttribute("loginStatusMessage", violation.getMessage());
            return;
        }

        PersistenceUtil<UserEntity> persistenceUtil = new PersistenceUtil<UserEntity>();

        boolean userExists = persistenceUtil.doNamedQuery("User.byName", UserEntity.class, new String[]{username}).size() > 0;
        if (userExists) {
            boolean passwordMatching = persistenceUtil.doNamedQuery("User.isMatching", UserEntity.class, new String[]{username, password}).size() > 0;
            if(passwordMatching){
                user = checkUser;

                PersistenceUtil<UrlsEntity> urlsPersistence = new PersistenceUtil<UrlsEntity>();
                urlsList = urlsPersistence.doNamedQuery("Urls.getAllByUsername", UrlsEntity.class, new String[]{username});

                response.sendRedirect("input.jsp");
            } else {
                request.getSession().setAttribute("loginStatusMessage", "Wrong password.");
                return;
            }
        } else {
            user = checkUser;
            persistenceUtil.save(checkUser);

            response.sendRedirect("input.jsp");
        }

        persistenceUtil.close();
    }

    public UserEntity getUser() {
        return user;
    }

    public ArrayList<UrlsEntity> getUrlsEntityList() {
        if(urlsList == null){
            return new ArrayList<UrlsEntity>();
        }

        return urlsList;
    }

    public ArrayList<String> getUrlsList(){
        ArrayList<String> urlsList = new ArrayList<>();

        for (UrlsEntity url :
                getUrlsEntityList()) {
            urlsList.add(url.getUrl());
        }

        return urlsList;
    }
}
