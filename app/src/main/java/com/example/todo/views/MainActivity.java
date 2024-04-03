package com.example.todo.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.todo.R;
import com.example.todo.adapter.MainPageAdapter;
import com.example.todo.base.PrimaryActivity;
import com.example.todo.dagger.ActivityComponent;
import com.example.todo.dagger.ActivityModule;
import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.presenter.MainHolder;
import com.example.todo.presenter.MainPresent;
import com.google.android.material.tabs.TabLayout;
import javax.inject.Inject;
import com.example.todo.dagger.DaggerActivityComponent;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends PrimaryActivity implements MainHolder.View {

    public static final String TAB="MainActivity";
    private ActivityComponent mActivityComponent;
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
        return R.layout.activity_main;
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
        mTab.setupWithViewPager(mVp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return mPresenter.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        mPresenter.onFabClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void initalizeInjector() {
        mActivityComponent=DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
    }


    @Override
    public int getCurrentViewPageView() {
        return 0;
    }

    @Override
    public void startActivityAndForResult(Intent intent, int requestCode) {

    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void setViewPagerAdapter(MainPageAdapter adapter) {

    }

    @Override
    public Intent getActivityIntent() {
        return null;
    }

    @Override
    public void setViewPageCurrentItem(int position, boolean a) {

    }

    @Override
    public void showAction(String message, String action, View.OnClickListener listener) {

    }

    @Override
    public void showDialog(int position, TaskDetailEntity entity) {

    }
}