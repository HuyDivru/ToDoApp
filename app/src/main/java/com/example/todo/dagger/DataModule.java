package com.example.todo.dagger;

import com.example.todo.data.DataDao;
import com.example.todo.data.PageFactory;
import com.example.todo.entities.MainPageItem;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    public List<MainPageItem> pages(){
        return PageFactory.createPages();
    }
    @Provides
    public DataDao dataDao(){
        return DataDao.getInstance();
    }
}
