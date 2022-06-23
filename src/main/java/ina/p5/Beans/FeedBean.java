package ina.p5.Beans;

import java.util.ArrayList;

public class FeedBean {
    String rssFeedURL;

    String title;
    String link;
    String description;
    String lastBuildDate;
    ArrayList<ItemBean> itemBeans = new ArrayList<>();

    /**
     * Represents a Feed
     *
     * @param title as title of rss feed
     * @param link to website
     * @param description of rss feed
     * @param lastBuildDate of rss feed
     */
    public FeedBean(String rssFeedURL, String title, String link, String description, String lastBuildDate) {
        this.rssFeedURL = rssFeedURL;
        this.title = title;
        this.link = link;
        this.description = description;
        this.lastBuildDate = lastBuildDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public ArrayList<ItemBean> getItemBeans() {
        return itemBeans;
    }

    @Override
    public String toString() {
        return "FeedBean: rssURL=" + rssFeedURL + " title=" + title + ", link=" + link + ", description=" + description + ", date=" + lastBuildDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedBean feedBean = (FeedBean) o;

        return rssFeedURL.equals(feedBean.rssFeedURL);
    }

    public void setRssFeedURL(String rssFeedURL) {
        this.rssFeedURL = rssFeedURL;
    }

    public String getRssFeedURL() {
        return rssFeedURL;
    }
}
