package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Format date.
     *
     * @param d date
     * @return formatted string date
     */
    public static String formatDate(Date d) {
        return (d == null ? "" : dateFormatter.format(d));
    }


    /**
     * Protect against html injection, replaceing < and > by their html code.
     *
     * @param name string to protect
     * @return string protected
     */
    public static String protectAgainstInjection(String name) {
        return name == null ? null : name.replace("&","&#38").replace("<", "&#60").replace(">", "&#62").replace("'","&#34");
    }
}
