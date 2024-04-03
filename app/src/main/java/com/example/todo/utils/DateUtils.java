package com.example.todo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static int callNextDayOfWeek(int currentDay){
        int result=(currentDay+1)%8;
        if(result==0){
            result=1;
        }
        return result;
    }


    /*
     class formatdatetimeholder
     */
    public static class FormatDateTimeHolder{
        static SimpleDateFormat sDateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("vi","VN"));
        static SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("vi","VN"));
        static SimpleDateFormat getsDateWeekFormat=new SimpleDateFormat("yyyy-MM-dd EEEE", new Locale("vi","VN"));
        static Date mDate=new Date();
    }
    public static  synchronized  String formatDateTime(long time){
        if (time<=0) throw new IllegalArgumentException("time must be greater than 0");
        FormatDateTimeHolder.mDate.setTime(time);
        return FormatDateTimeHolder.sDateTimeFormat.format(FormatDateTimeHolder.mDate);
    }
    public static synchronized String formatDate(long time){
        if (time<=0) throw new IllegalArgumentException("time must be greater than 0");
        FormatDateTimeHolder.mDate.setTime(time);
        return FormatDateTimeHolder.sDateFormat.format(FormatDateTimeHolder.mDate);
    }
    public static synchronized String getDateTime(long time){
        if (time<=0) throw new IllegalArgumentException("time must be greater than 0");
        FormatDateTimeHolder.mDate.setTime(time);
        return FormatDateTimeHolder.getsDateWeekFormat.format(FormatDateTimeHolder.mDate);
    }
//    public
    //Test
}
