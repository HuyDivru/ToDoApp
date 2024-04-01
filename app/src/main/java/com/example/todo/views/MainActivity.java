package com.example.todo.views;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.todo.R;
import com.example.todo.base.PrimaryActivity;
import com.example.todo.presenter.MainPresent;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends PrimaryActivity {

    public static final String TAB="MainActivity";

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mVp;
    @BindView(R.id.main_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Inject
    MainPresent mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.bindView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    public void initalizeInjector() {

    }
}