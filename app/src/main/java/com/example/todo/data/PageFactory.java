package com.example.todo.data;

import com.example.todo.entities.MainPageItem;
import com.example.todo.fragments.PageFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PageFactory {
    private static List <MainPageItem> items=new ArrayList<>();

    public static List<MainPageItem> createPages(){
        if(items==null || items.size()!=7){
            items=new ArrayList<>();
            items.add(new MainPageItem(Calendar.SUNDAY,"SUNDAY",new PageFragment()));
            items.add(new MainPageItem(Calendar.MONDAY,"MONDAY",new PageFragment()));
            items.add(new MainPageItem(Calendar.TUESDAY,"TUESDAY",new PageFragment()));
            items.add(new MainPageItem(Calendar.WEDNESDAY,"WEDNESDAY",new PageFragment()));
            items.add(new MainPageItem(Calendar.THURSDAY,"THURSDAY",new PageFragment()));
            items.add(new MainPageItem(Calendar.FRIDAY,"THURSDAY",new PageFragment()));
            items.add(new MainPageItem(Calendar.SATURDAY,"SATURDAY",new PageFragment()));
        }
        return items;
    }

}
