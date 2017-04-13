package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

	public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static String formatDate(Date d) {
		return (d == null ? "NULL" : dateFormatter.format(d));		
	}
	
	public static Date dateFromString(String s) {
		try {
			
			return dateFormatter.parse(s);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	
	public static String quotedOrNull(Date value) {
		if(value == null) {
			return "NULL";
		} else {
			return "'"+formatDate(value)+"'";
		}
	}
	
	public static String quotedOrNull(Integer value) {
		if(value == null) {
			return "NULL";
		} else {
			return "'"+value+"'";
		}
	}
	
	public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return date == null ? null : new java.sql.Date(date.getTime());
	}
}
