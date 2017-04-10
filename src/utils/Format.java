package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

	public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static String formatDateForSql(Date d) {
		return (d == null ? "NULL" : dateFormatter.format(d));		
	}
	
	
}
