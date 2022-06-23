package ina.p5.Beans;

import ina.p5.RSSFeedParser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Java Bean: UrlsBean
 * Packs a name and as list of urls.
 * Checks urls if valid and adds a status message in another list.
 */
public class UrlsBean {
    private final ArrayList<String> urls = new ArrayList<>();
    private final ArrayList<String> statusMessages = new ArrayList<>();

    public UrlsBean(){

    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public ArrayList<String> getStatusMessages() {
        return statusMessages;
    }

    /**
     * Adds an url with a statusmessage to the UrlsBean.
     * Used when the statusmessage if already known.
     *
     * @param url
     * @param message
     */
    public  void addURL(String url, String message){
        urls.add(url);
        statusMessages.add(message);
    }

    /**
     * Checks an url and adds status messages
     *
     * @param url
     * @return
     */
    public boolean addUrl(String url){
        String patter = "^(http|https)://.*$";
        if(!url.matches(patter)) {
            url = "https://" + url;
        }

        try {
            if(!RSSFeedParser.getRSSFeed(url).isEmpty()){
                urls.add(url);
                statusMessages.add("RSS Feed found. Saved into your account.");
                return true;
            } else {
                urls.add(url);
                statusMessages.add("No RSS Feed found.");
            }
        } catch (IOException e) {
            urls.add(url);
            statusMessages.add("URL is invalid.");
        }

        return false;
    }

    public String getHTTPUrl(String url){
        String patter = "^(http|https)://.*$";
        if(!url.matches(patter)) {
           return "https://" + url;
        }
        return url;
    }
}
