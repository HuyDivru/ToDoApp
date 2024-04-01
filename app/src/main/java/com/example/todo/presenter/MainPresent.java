package com.example.todo.presenter;

import android.content.Context;

import com.example.todo.adapter.MainPageAdapter;
import com.example.todo.data.DataDao;
import com.example.todo.entities.MainPageItem;

import java.util.List;

import javax.inject.Inject;

public class MainPresent implements MainHolder.Presenter{
    public static final String TAG = "MainPresent";
    private Context  mContext;
    private int mLastClickPosition ;

    @Inject
    List<MainPageItem> mItems;
    @Inject
    MainPageAdapter mainPageAdapter;
    @Inject
    DataDao mDataDao;

    @Inject
    public MainPresent(Context context){
        mContext = context;
    }


}
