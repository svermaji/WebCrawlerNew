package org.sv.webcrawler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.sv.webcrawler.helper.LinkCollector;
import org.sv.webcrawler.util.Utils;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebCrawlerTest {

    @Mock
    private LinkCollector linkCollector;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStartCrawling () {
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.setDomain(Utils.W3SCHOOLS_SITE);
        webCrawler.setLinkCollector(linkCollector);

        when (linkCollector.getPageLinks(anyString())).thenReturn(TestData.getMockLinks());

        webCrawler.startCrawling();

        Assert.assertEquals(webCrawler.toString(), "Total links visited = 10");
    }

}