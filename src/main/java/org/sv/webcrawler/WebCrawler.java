package org.sv.webcrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sv.webcrawler.helper.LinkCollector;
import org.sv.webcrawler.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple web crawler that will crawl through a website
 * considering root url provided
 */
public class WebCrawler {

    private String domain;

    private Logger logger;
    private boolean debug;
    private LinkCollector linkCollector;

    /**
     * linksVisited - once any link is visited, to avoid its repetition stored here
     * linksToVisit - links still pending to visit are here
     * ignoredLinks - links which do not satisfy validEndUrls condition are present here
     * tempLinksToProcess - temp holder to which refilled from linksToVisit
     */
    private List<String> linksVisited, linksToVisit, ignoredLinks, tempLinksToProcess;

    // After reading these many links stop reading
    private final int THRESHOLD = 10;
    // tempLinksToProcess will be filled with below number of items from linksToVisit at a time
    private final int LINK_TO_VISIT_CHUNK = 5;
    // Keep tracks of number of processed files count
    private int processedFilesCount = 0;

    public static void main(String[] args) {
        WebCrawler webCrawler = new WebCrawler();

        webCrawler.setDomain(Utils.W3SCHOOLS_SITE);
        webCrawler.setLinkCollector(new LinkCollector());

        webCrawler.startCrawling();
    }

    public void startCrawling() {
        logger = LoggerFactory.getLogger(this.getClass());
        debug = logger.isDebugEnabled();
        init();
    }

    public void setLinkCollector(LinkCollector linkCollector) {
        this.linkCollector = linkCollector;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Initialize variables
     */
    private void init() {

        linksVisited = new ArrayList<>();
        linksToVisit = new ArrayList<>();
        ignoredLinks = new ArrayList<>();
        tempLinksToProcess = new ArrayList<>();

        linksToVisit.add(domain);
        fillLinksToProcess();

        crawl();
    }

    /**
     * refills links to process
     */
    private void fillLinksToProcess() {
        synchronized (WebCrawler.class) {
            tempLinksToProcess.clear();
            if (linksToVisit.size() > LINK_TO_VISIT_CHUNK) {
                for (int i = 0; i < LINK_TO_VISIT_CHUNK; i++) {
                    tempLinksToProcess.add(linksToVisit.get(i));
                }
            } else {
                tempLinksToProcess.addAll(linksToVisit);
            }
            linksToVisit.removeAll(tempLinksToProcess);

            if (debug) {
                logger.debug("Temporary links to process are: " + tempLinksToProcess.size());
                logger.debug("Links still to visit are: " + linksToVisit.size());
            }
        }

    }

    /**
     * Crawling process starts here
     */
    private void crawl() {
        for (String linkToProcess : tempLinksToProcess) {

            if (Utils.hasValue(linkToProcess)) {
                if (debug) {
                    logger.debug("Processing link [" + linkToProcess + "]");
                }
                if (processedFilesCount >= THRESHOLD) {
                    if (debug) {
                        logger.debug("Thresh hold reached, skipping remaining links, after [" + linkToProcess + "]");
                        logger.debug(getListInfoToLog(tempLinksToProcess));
                    }
                    break;
                }

                processedFilesCount++;

                if (!linksVisited.contains(linkToProcess)) {
                    linksVisited.add(linkToProcess);
                }

                // collect links of a specific page
                processPageLinks(linkCollector.getPageLinks(linkToProcess));
            } else {
                logger.warn("Link empty. Skipping");
            }
        }

        if (debug) {
            logger.debug("Counter [" + processedFilesCount + "], threshold [" + THRESHOLD + "]");
        }

        if (processedFilesCount < THRESHOLD) {
            fillLinksToProcess();
            if (tempLinksToProcess.size() > 0) {
                crawl();
            }
        } else {
            if (debug) {
                logger.debug(getReport());
            }
        }
    }

    /**
     * Links collected on a page are filtered here
     *
     * @param pageLinks list
     */
    private void processPageLinks(List<String> pageLinks) {
        pageLinks.forEach(s -> {
            if (Utils.hasValue(s) && !linksVisited.contains(s) && !linksToVisit.contains(s)) {
                linksToVisit.add(s);
            }
        });
    }

    private String getListInfoToLog(List<String> links) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total links = ").append(links.size());
        sb.append(Utils.NEW_LINE);
        sb.append("Links = ").append(getLinks(links));
        return sb.toString();
    }

    private String getLinks(List<String> list) {
        return list.toString().replaceAll(Utils.COMMA_SPACE, Utils.NEW_LINE);
    }

    private String getReport() {
        StringBuilder sb = new StringBuilder();

        sb.append("Final report");
        sb.append(Utils.NEW_LINE);
        sb.append("============");
        sb.append(Utils.NEW_LINE);
        sb.append("Links visited information");
        sb.append(Utils.NEW_LINE);
        sb.append(getListInfoToLog(linksVisited));
        sb.append(Utils.NEW_LINE);
        sb.append("----------------------------------------------------------");
        sb.append(Utils.NEW_LINE);
        sb.append("Links to visit information");
        sb.append(Utils.NEW_LINE);
        sb.append(getListInfoToLog(linksToVisit));
        sb.append(Utils.NEW_LINE);
        sb.append("----------------------------------------------------------");
        sb.append(Utils.NEW_LINE);
        sb.append("Ignored links information");
        sb.append(Utils.NEW_LINE);
        sb.append(getListInfoToLog(ignoredLinks));

        return sb.toString();
    }

    public String toString() {
        return "Total links visited = " + linksVisited.size();
    }
}