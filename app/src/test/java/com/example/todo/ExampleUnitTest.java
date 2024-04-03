package com.example.todo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int day= Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        System.out.println("Ngày Trong Tuần "+ (day-1));
    }
    @Test
    public void testNgay(){
        int currentDay=8;
        int nextDay= (currentDay+1)%8;
        if(nextDay==0){
            nextDay=1;
        }
        System.out.println("ngay trong tuan "+ nextDay);
    }
    @Test
    public void test(){
        System.out.println(8%8);
    }
}