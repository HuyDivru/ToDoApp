package com.example.todo.data;

import com.example.todo.R;

import java.util.Arrays;
import java.util.List;

public class ImageFactory {
    public ImageFactory() {
    }
    private static final String[] ids=new String[]{"bg_note_min.jpg"};


    public static List<String> createBgImgs(){
        return Arrays.asList(ids);
    }
    private static final int[] priorityIcons=new int[]{
            R.drawable.ic_priority,R.drawable.ic_priority_1,
            R.drawable.ic_priority_2,R.drawable.ic_priority_3
            ,R.drawable.ic_priority_4,R.drawable.ic_priority_5
    };
    public static int[] createIcons() {
        return priorityIcons;
    }
}
