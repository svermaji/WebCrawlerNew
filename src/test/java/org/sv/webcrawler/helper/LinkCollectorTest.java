package org.sv.webcrawler.helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.sv.webcrawler.DocDownLoader;
import org.sv.webcrawler.TestData;
import org.sv.webcrawler.util.Utils;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LinkCollectorTest {

    @Mock
    private DocDownLoader docDownLoader;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPageLinks() {
        LinkCollector linkCollector = new LinkCollector();
        linkCollector.setDocDownLoader(docDownLoader);

        when(docDownLoader.getAbsHrefLinks(anyString())).thenReturn(TestData.getMockLinks());

        List<String> links = linkCollector.getPageLinks(Utils.W3SCHOOLS_SITE);
        Assert.assertEquals(links.size(), 14);
    }

}
