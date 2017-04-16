package utils;

import java.text.ParseException;
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
        return (d == null ? "NULL" : dateFormatter.format(d));
    }

    /**
     * Create a date from string with format yyyy-MM-dd.
     *
     * @param s String to parse
     * @return Date
     */
    public static Date dateFromString(String s) {
        try {
            return dateFormatter.parse(s);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * Map java.util.Date to java.sql.Date.
     *
     * @param date Date class java.util.date
     * @return date
     */
    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }
}
