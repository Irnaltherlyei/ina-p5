package ina.p5.Beans;

public class ItemBean {
    String title;
    String link;

    /**
     * Represents an article.
     *
     * @param title as title of article
     * @param link as url to article
     */
    public ItemBean(String title, String link){
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "\n" + title + ", " + link;
    }
}
