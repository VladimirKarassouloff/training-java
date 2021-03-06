package cdb.utils;

import java.text.SimpleDateFormat;

public class Format {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * Protect against html injection, replaceing < and > by their html code.
     *
     * @param name string to protect
     * @return string protected
     */
    public static String protectAgainstInjection(String name) {
        return name == null ? null : name.replace("&", "&#38").replace("<", "&#60").replace(">", "&#62").replace("'", "&#34");
    }
}
