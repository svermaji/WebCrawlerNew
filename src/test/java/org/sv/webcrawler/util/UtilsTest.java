package org.sv.webcrawler.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void testHasValue () {
        Assert.assertTrue (Utils.hasValue("Not Empty"));
    }

    @Test
    public void testEmptyValue () {
        Assert.assertFalse (Utils.hasValue(""));
    }

    @Test
    public void testNullValue () {
        Assert.assertFalse (Utils.hasValue(null));
    }

}
