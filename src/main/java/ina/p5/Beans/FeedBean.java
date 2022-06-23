package ina.p5.Beans;

import java.util.ArrayList;

public class FeedBean {
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
    public FeedBean(String title, String link, String description, String lastBuildDate) {
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
        return "FeedBean: title=" + title + ", link=" + link + ", description=" + description + ", date=" + lastBuildDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedBean feedBean = (FeedBean) o;

        if (!title.equals(feedBean.title)) return false;
        return link.equals(feedBean.link);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + link.hashCode();
        return result;
    }
}
