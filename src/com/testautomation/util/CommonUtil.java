package com.testautomation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

	public boolean compareDate(String submissionDate, String completionDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(submissionDate);
        Date date2 = sdf.parse(completionDate);

        System.out.println("submissionDate : " + sdf.format(date1));
        System.out.println("completionDate : " + sdf.format(date2));

       if (date1.before(date2) || date1.equals(date2)) {
            return true;
        } else {
            return false;
        }

    }
	
	public String toDate() {
		Date date = new Date();
		String toDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		return toDate;
	}
	public String fromDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, - Integer.parseInt(System.getProperty("timeinterval")));
		Date backHour = cal.getTime();
		String fromDate= new SimpleDateFormat("yyyy-MM-dd").format(backHour);
		return fromDate;
	}
	
}
