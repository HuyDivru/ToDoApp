package com.example.todo.utils;

import java.util.Calendar;

public class DateUtils {
    public DateUtils(){

    }

    public static long getFirstModayTimeMillisOfWeek() {
        Calendar calendar=Calendar.getInstance();
        int firstDayOfWeek=calendar.getFirstDayOfWeek();

        int daysFromMonday=calendar.get(Calendar.DAY_OF_WEEK);
        long secondOfToday=calendar.get(Calendar.HOUR_OF_DAY)*60*60+calendar.get(Calendar.MINUTE)*60+calendar.get(Calendar.SECOND);

        long millisFromMonday = ((daysFromMonday - firstDayOfWeek + 7) % 7) * 24 * 60 * 60 * 1000 + secondOfToday * 1000;

        return System.currentTimeMillis()-millisFromMonday;
    }
}
