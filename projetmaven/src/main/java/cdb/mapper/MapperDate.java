package cdb.mapper;

import cdb.utils.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by vkarassouloff on 20/04/17.
 */
public class MapperDate {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperDate.class);


    /**
     * Create a date from string with format yyyy-MM-dd.
     *
     * @param s String to parse
     * @return Date
     */
    public static Date dateFromString(String s) {
        try {
            return Format.DATE_FORMATTER.parse(s);
        } catch (ParseException e) {
            LOGGER.info("MapperDate : failed to map date => " + s);
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
        try {
            return (d == null ? "" : Format.DATE_FORMATTER.format(d));
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.info("MapperDate 2 : failed to map date => " + d);
            return null;
        }
    }


}
