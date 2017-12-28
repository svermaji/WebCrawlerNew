package org.sv.webcrawler;

import org.junit.Assert;
import org.junit.Test;
import org.sv.webcrawler.util.Utils;

import java.util.List;

public class DocDownLoaderTest {

    @Test
    public void testGetZeroLinks () {
        DocDownLoader docDownLoader = new DocDownLoader();
        List<String> links = docDownLoader.getAbsHrefLinks("bad.url");
        Assert.assertEquals(links.size(), 0);
    }

    @Test
    public void testGetLinks () {
        DocDownLoader docDownLoader = new DocDownLoader();
        List<String> links = docDownLoader.getAbsHrefLinks(Utils.W3SCHOOLS_SITE);
        Assert.assertTrue(links.size() > 0);
    }

}

