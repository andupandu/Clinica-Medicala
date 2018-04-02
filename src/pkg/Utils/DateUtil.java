package pkg.Utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static java.sql.Date getDateFromString(String date) throws ParseException{
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Date parsed = format.parse(date);
	        java.sql.Date sql = new java.sql.Date(parsed.getTime());
	        return sql;
	}
	
	public static java.sql.Date getSqlDateFromUtilDate(Date data){
	    java.sql.Date sqlDate = new java.sql.Date(data.getTime());
	    return sqlDate;
	}

}
