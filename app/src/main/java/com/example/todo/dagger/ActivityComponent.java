package com.example.todo.dagger;

import android.widget.ListAdapter;

import com.example.todo.presenter.MainPresent;
import com.example.todo.views.AboutActivity;
import com.example.todo.views.ListActivity;
import com.example.todo.views.MainActivity;

import dagger.Component;

@Component(modules = {ActivityModule.class,DataModule.class,EntityModule.class})
public interface ActivityComponent {


    void inject(MainActivity mainActivity);
    void inject(AboutActivity aboutActivity);
    void inject(MainPresent mPresent);
    void inject(ListActivity listActivity);
    void inject(ListAdapter adapter);
}
