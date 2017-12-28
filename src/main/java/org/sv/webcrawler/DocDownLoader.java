package org.sv.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sv.webcrawler.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will help in downloading bunch of urls that
 * may point to resource like images, pdfs etc.
 */
public class DocDownLoader {

    private Logger logger;
    private boolean debug;

    public DocDownLoader() {
        logger = LoggerFactory.getLogger(this.getClass());
        debug = logger.isDebugEnabled();
    }

    /**
     * Downloads html doc from http url and return as {@link Document} object
     * Making public from reusability point of view
     *
     * @param url - http url to download
     * @return html document
     */
    public Document download(String url) {

        if (debug) {
            logger.debug("Trying url [" + url + "]");
        }

        long startTime = System.currentTimeMillis();
        try {
            Document doc = Jsoup.connect(url).get();

            long diffTime = (System.currentTimeMillis() - startTime);
            long diffTimeInSec = diffTime / 1000;

            if (debug) {
                logger.debug("Download completed in [" + diffTimeInSec + "] seconds.");
                logger.debug("File downloaded");
            }
            return doc;
        } catch (Exception e) {
            logger.error("Error in downloading.  Details: " + e.getMessage());
        }
        return null;
    }

    /**
     * Collect {@link Elements} from {@link Document}
     * Making public from reusability point of view
     *
     * @param doc Document object
     * @return Elements
     */
    public Elements getHrefLinks(Document doc) {
        return doc.select("a[href]");
    }

    /**
     * Convert elements to List that has absolute urls
     * from attribute href
     * Making public from reusability point of view
     *
     * @param elements object
     * @return list of urls
     */
    public List<String> makeAbsHrefLinks(Elements elements) {
        List<String> list = new ArrayList<>();
        elements.forEach(element -> list.add(element.attr("abs:href")));
        return list;
    }

    /**
     * Helper method that downloads html page, process elements
     * and create list of urls
     *
     * @param url - http url
     * @return - list of urls
     */
    public List<String> getAbsHrefLinks(String url) {
        Document doc = download(url);
        if (doc == null) {
            return new ArrayList<>();
        }
        return makeAbsHrefLinks(getHrefLinks(doc));
    }

}
