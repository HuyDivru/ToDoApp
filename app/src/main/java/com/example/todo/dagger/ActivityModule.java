package com.example.todo.dagger;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }
    @Provides
    public AppCompatActivity getActivity(){
        return mActivity;
    }
    @Provides
    public Context getContext(){
        return mActivity;
    }
    @Provides
    public FragmentManager fragmentManager(){
        return mActivity.getSupportFragmentManager();
    }
}
