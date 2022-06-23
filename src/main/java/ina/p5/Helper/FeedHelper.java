package ina.p5.Helper;

import ina.p5.Beans.UrlsEntity;
import ina.p5.PersistenceUtil;
import ina.p5.RSSFeedParser;
import ina.p5.Beans.FeedBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class FeedHelper extends HelperBase {
    protected ArrayList<FeedBean> feeds = new ArrayList<>();

    public FeedHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     * Gets all urls of the user from the database and displays its rss feed.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("feedHelper") == null) {
            request.getSession().setAttribute("feedHelper", this);
        }

        LoginHelper loginHelper = (LoginHelper) request.getSession().getAttribute("loginHelper");
        if(loginHelper == null || loginHelper.getUser() == null){
            // User is not logged in
            return;
        }

        // Get username
        String username = loginHelper.getUser().getUsername();

        ArrayList<String> urls = new ArrayList<>();

        // Get feed urls from database
        PersistenceUtil<UrlsEntity> urlsPersistence = new PersistenceUtil<>();
        ArrayList<UrlsEntity> urlsList = urlsPersistence.doNamedQuery("Urls.getAllByUsername", UrlsEntity.class, new String[]{username});

        for (UrlsEntity url :
                urlsList) {
            urls.add(url.getUrl());
        }

        for (String url : urls) {
            ArrayList<String> rssList;
            try {
                rssList = RSSFeedParser.getRSSFeed(url);
            } catch (Exception e) {
                continue;
            }

            for (String feedUrl : rssList) {
                //System.out.println(feedUrl);
                RSSFeedParser parser = new RSSFeedParser(feedUrl);
                FeedBean feed = parser.readFeed();
                //System.out.println(feed.getLink());
                if(!feeds.contains(feed)){
                    feeds.add(feed);
                }
            }
        }

        if (!feeds.isEmpty()) {
            response.sendRedirect("feeds.jsp");
        }
    }

    public ArrayList<FeedBean> getFeeds() {
        return feeds;
    }
}