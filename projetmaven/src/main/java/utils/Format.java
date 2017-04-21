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


}
