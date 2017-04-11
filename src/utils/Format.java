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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
