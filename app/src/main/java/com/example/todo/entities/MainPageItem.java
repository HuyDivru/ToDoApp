package com.example.todo.entities;

import androidx.fragment.app.Fragment;


/**
 * create by honhathuy on 2024/03/29
 */
public class MainPageItem
{

    private int dayOfTheWeek;
    private String title;
    private Fragment mFragment;

    public MainPageItem(int dayOfTheWeek, String title, Fragment mFragment) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.title = title;
        this.mFragment = mFragment;
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }
}
