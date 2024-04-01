package com.example.todo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.todo.entities.MainPageItem;

import java.util.List;

import javax.inject.Inject;

/*
    create by Ho Nhat Huy 2024/03/30
 */
public class MainPageAdapter extends FragmentPagerAdapter {
    private List<MainPageItem> mPageItems;

    @Inject
    public MainPageAdapter(@NonNull FragmentManager fm,List<MainPageItem> pageItems){
        super(fm);
        mPageItems = pageItems;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mPageItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mPageItems.size();
    }

    public CharSequence getPageTitle(int position){
        return mPageItems.get(position).getTitle();
    }
}
