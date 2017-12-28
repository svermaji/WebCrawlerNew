package org.sv.webcrawler.util;

import org.apache.commons.lang3.StringUtils;

public class Utils {

    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String COMMA_SPACE = COMMA + SPACE;
    public static final String W3SCHOOLS_SITE = "https://www.w3schools.com";
    public static final String NEW_LINE = System.lineSeparator();

    /**
     * return true if param has non-null value
     * This way even if we need to make any specific change
     * we can achieve from this central place
     *
     * @param item string to be checked
     * @return boolean status of operation
     */
    public static boolean hasValue(String item) {
        return StringUtils.isNotEmpty(item);
    }

}
