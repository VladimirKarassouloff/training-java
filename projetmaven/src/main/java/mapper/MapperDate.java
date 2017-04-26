package mapper;

import utils.Format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vkarassouloff on 20/04/17.
 */
public class MapperDate {


    /**
     * Create a date from string with format yyyy-MM-dd.
     *
     * @param s String to parse
     * @return Date
     */
    public static Date dateFromString(String s) {
        try {
            return Format.dateFormatter.parse(s);
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


    /**
     * Format date.
     *
     * @param d date
     * @return formatted string date
     */
    public static String formatDate(Date d) {
        return (d == null ? "" : Format.dateFormatter.format(d));
    }


}
