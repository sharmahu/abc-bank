package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public Date oldDate(int numberOfDays) {
        Calendar previous = Calendar.getInstance();
        previous.add(Calendar.DATE, -numberOfDays);
		return previous.getTime();       
        
    }
}
