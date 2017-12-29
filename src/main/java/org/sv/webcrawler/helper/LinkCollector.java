package org.sv.webcrawler.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sv.webcrawler.DocDownLoader;

import java.util.List;

/**
 * A simple web crawler that will crawl through a website
 * considering root url provided
 */
public class LinkCollector {

    private Logger logger;
    private boolean debug;

    private DocDownLoader docDownLoader;

    public LinkCollector() {
        logger = LoggerFactory.getLogger(this.getClass());
        debug = logger.isDebugEnabled();
        setDocDownLoader(new DocDownLoader());
    }

    public void setDocDownLoader(DocDownLoader docDownLoader) {
        this.docDownLoader = docDownLoader;
    }

    public List<String> getPageLinks(String url) {
        if (debug) {
            logger.debug("Retrieving page links for url [" + url + "]");
        }
        // Used helper method from docDownloader
        // but can be used three separate methods
        // to get document then Elements and last links
        return docDownLoader.getAbsHrefLinks(url);
    }

}
