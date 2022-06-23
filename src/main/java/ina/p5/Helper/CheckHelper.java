package ina.p5.Helper;

import ina.p5.Beans.UrlsBean;
import ina.p5.Beans.UrlsEntity;
import ina.p5.Beans.UserEntity;
import ina.p5.PersistenceUtil;
import ina.p5.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Map;

public class CheckHelper extends HelperBase {
    protected UrlsBean urlsBean = new UrlsBean();

    public CheckHelper(HttpServletRequest request, HttpServletResponse response){
        super(request, response);
    }

    /**
     * Checks all urls which the user typed.
     * Redirects back to the same page and displays status messages.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("checkHelper") == null){
            request.getSession().setAttribute("checkHelper", this);
        }

        // Checks if the user is logged in. If not redirect to login page.
        LoginHelper loginHelper = (LoginHelper) request.getSession().getAttribute("loginHelper");
        if(loginHelper == null || loginHelper.getUser() == null){
            // User is not logged in
            response.sendRedirect("index.jsp");
            return;
        }

        // Get UserEntity
        UserEntity userEntity = loginHelper.getUser();

        // Persistence
        PersistenceUtil<UrlsEntity> persistenceUtil = new PersistenceUtil<>();

        // Validation
        ValidationUtil<UrlsEntity> validationUtil = new ValidationUtil<>();

        // Deletes all entries in urls where username.
        persistenceUtil.doUpdateSQLQuery("DELETE FROM URLS WHERE USERNAME = ?1", new String[]{userEntity.getUsername()});

        urlsBean = new UrlsBean();

        // Checks all URLs and sets Statusmessages for every single URL displaying them on the input.jsp
        Map<String, String[]> parameters = request.getParameterMap();
        urlLoop : for(String parameter : parameters.keySet()) {
            if(parameter.toLowerCase().startsWith("url")) {
                String username = userEntity.getUsername();
                String url = request.getParameter(parameter);

                UrlsEntity urlsEntity = new UrlsEntity();
                urlsEntity.setUsername(username);
                urlsEntity.setUrl(url);

                if(!validationUtil.isValid(urlsEntity)){
                    // First check if url has valid pattern
                    for (ConstraintViolation<UrlsEntity> violation :
                            validationUtil.getViolations()) {
                        urlsBean.addURL(url, violation.getMessage());
                        continue urlLoop;
                    }
                } else {
                    // Second check if url has an RSS Feed
                    if(!urlsBean.addUrl(url)){
                        continue;
                    }
                }

                // Returns the url with https:// prefix
                url = urlsBean.getUrls().get(urlsBean.getUrls().size() - 1);

                // Save every url in database
                boolean alreadySaved = persistenceUtil.doNamedQuery("Urls.byName", UrlsEntity.class, new String[]{url}).size() > 0;
                if(!alreadySaved){
                    urlsEntity.setUrl(url);
                    persistenceUtil.save(urlsEntity);
                }
            }
        }

        persistenceUtil.close();
    }

    public UrlsBean getUrlsBean() {
        return urlsBean;
    }
}
