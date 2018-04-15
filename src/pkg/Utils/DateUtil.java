package pkg.Utils;

import java.util.Calendar;
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
	public static Date getDateHourFromString(String date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date parsed = format.parse(date);
        return parsed;
	}
	public static String addMinutesToHours(String hour,String minutes) throws ParseException {
		Date date=getDateHourFromString(hour);
		Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.MINUTE, getMinutesFromStringHour(minutes));
		 String newTime = new SimpleDateFormat("HH:mm").format(cal.getTime());
		 return newTime;
	}
	public static int getMinutesFromStringHour(String hour) {
		int hours = Integer.parseInt(hour.substring(0, 2));
		int minute = Integer.parseInt(hour.substring(3));
		return (hours*60)+minute;
	}
	public static Date addMonthToCurrentDate(String date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
	}
}
