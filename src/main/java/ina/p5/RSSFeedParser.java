package ina.p5;

import ina.p5.Beans.FeedBean;
import ina.p5.Beans.ItemBean;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

public class RSSFeedParser {
    private String rssFeedURL = "";
    private HttpResponse<InputStream> response;

    /**
     *
     * @param feedUrl as url
     * @throws HttpStatusException thrown if status is not 200 - OK
     */
    public RSSFeedParser(String feedUrl) throws HttpStatusException {
        this.rssFeedURL = feedUrl;

        response = getHTTPResponse(feedUrl);

        int status = response.statusCode();
        if(status == 301 || status == 302) {
            Optional<String> opt = response.headers().firstValue("Location");
            if (opt.isPresent()){
                String redirect = opt.get();
                System.out.println("\u001B[35m" + "Redirect to: " + redirect + "\u001B[0m");
                response = getHTTPResponse(redirect);
            }
        }
        else if(response.statusCode() != 200) {
            throw new HttpStatusException("Statuscode not OK", status, feedUrl);
        }
    }

    /**
     * Function to retrieve html data with simple GET request.
     *
     * @param url as url
     * @return HttpResponse as an InputStream
     */
    public HttpResponse<InputStream> getHTTPResponse(String url){
        String patter = "^(http|https)://.*$";
        if(!url.matches(patter)) {
            url = "https://" + url;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    /**
     * Parses an xml rss feed into a ina.p4.beans.FeedBean class.
     * First fills the rss feed header and after retrieves all the different articles as items.
     *
     * @return ina.p4.beans.FeedBean class
     */
    public FeedBean readFeed() {
        FeedBean feed = null;
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            XMLEventReader eventReader = inputFactory.createXMLEventReader(response.body());

            // Get header only
            String headerTitle = "";
            String headerLink = "";
            String headerDescription = "";
            String headerDate = "";

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.peek();

                if(event.isStartElement()){
                    String attribute = event.asStartElement().getName().getLocalPart();

                    if ("title".equals(attribute)) {
                        eventReader.nextEvent();
                        event = eventReader.peek();
                        if (event.isCharacters()) {
                            headerTitle = event.asCharacters().getData();
                        }
                    }
                    else if ("link".equals(attribute)) {
                        eventReader.nextEvent();
                        event = eventReader.peek();
                        if (event.isCharacters()) {
                            headerLink = event.asCharacters().getData();
                        }
                    }
                    else if ("desciption".equals(attribute)) {
                        eventReader.nextEvent();
                        event = eventReader.peek();
                        if (event.isCharacters()) {
                            headerDescription = event.asCharacters().getData();
                        }
                    }
                    else if ("lastBuildDate".equals(attribute)) {
                        eventReader.nextEvent();
                        event = eventReader.peek();
                        if (event.isCharacters()) {
                            headerDate = event.asCharacters().getData();
                        }
                    }
                    else if ("item".equals(attribute)) {
                        feed = new FeedBean(rssFeedURL, headerTitle, headerLink, headerDescription, headerDate);
                        break;
                    }
                }
                eventReader.nextEvent();
            }

            // Get all items
            String title = "";
            String link = "";
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.peek();

                if(event.isStartElement()){
                    String attribute = event.asStartElement().getName().getLocalPart();
                    //System.out.println(event.asStartElement().getName().getLocalPart());

                    switch (attribute){
                        case "title": {
                            eventReader.nextEvent();
                            event = eventReader.peek();
                            if(event.isCharacters()){
                                title = event.asCharacters().getData();
                            }
                        }
                        case "link": {
                            eventReader.nextEvent();
                            event = eventReader.peek();
                            if(event.isCharacters()){
                                link = event.asCharacters().getData();
                            }
                        }
                    }
                } else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("item")){
                    feed.getItemBeans().add(new ItemBean(title, link));
                }
                eventReader.nextEvent();
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return feed;
    }

    /**
     * Returns a list of all rss feed links from an url.
     * A single url can have multiple rss feeds.
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static ArrayList<String> getRSSFeed(String url) throws IOException {
        ArrayList<String> rssList = new ArrayList<>();

        String rssPath;

        Elements elements = Jsoup.connect(url).get().getElementsByTag("link");

        for(Element e: elements)
        {
            if(!e.attr("type").contains("application/rss+xml")){
                continue;
            }
            rssPath = e.attr("href");

            if(rssPath.contains("http")){
                rssList.add(rssPath);
                continue;
            }

            if(rssPath.startsWith("/") && url.endsWith("/")){
                rssPath = rssPath.substring(1);
            }

            int index = url.indexOf(".");
            index = url.indexOf("/", index);
            if(index >= 0){
                url = url.substring(0, index + 1);
            }

            rssPath = url + rssPath;
//            if(rssPath.startsWith("/")){
//                // Keep it or add
//            }
//            if(url.endsWith("/")){
//                // Remove it
//            }
//            System.out.println("-----------");
//            System.out.println(url);
//            System.out.println(rssPath);
//
//            if(rssPath.startsWith("https://")){
//
//            } else {
//                rssPath = url + rssPath;
//            }
//
//            System.out.println(rssPath);

            rssList.add(rssPath);
        }
        return rssList;
    }
}