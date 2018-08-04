package com.szx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	// 将时分秒,毫秒域清零
	public static Date getDateWithoutTime(Date d) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		// 将时分秒,毫秒域清零
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
   }
	
	public static Date changeStringToDate(String dateString,String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = sdf.parse(dateString);
		return d;
	}
}
