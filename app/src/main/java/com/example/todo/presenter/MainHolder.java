package com.example.todo.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.todo.adapter.MainPageAdapter;
import com.example.todo.base.PrimaryPresenter;
import com.example.todo.base.PrimaryView;
import com.example.todo.entities.TaskDetailEntity;

public interface MainHolder {
    interface View extends PrimaryView {
        int getCurrentViewPageView();
        void startActivityAndForResult(Intent intent, int requestCode);
        void finishActivity();
        void setViewPagerAdapter(MainPageAdapter adapter);
        Intent getActivityIntent();
        void setViewPageCurrentItem(int position,boolean a);
        void showAction(String message,String action,android.view.View.OnClickListener listener);
        void showDialog(int position, TaskDetailEntity entity);

    }
    interface Presenter extends PrimaryPresenter{
        void onBackPressed();
        void onFabClick();
        boolean onOptionsItemSelected(MenuItem item);
        void onCreate(Bundle savedInstanceState);
        void onDestroy();
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void onListTaskItemLongClick(int position, TaskDetailEntity entity);
        void onListTaskItemClick(int position, TaskDetailEntity entity);
        void dialogActionItemClick(int position, TaskDetailEntity entity);
        void dialogActionDeleteTask(int position,TaskDetailEntity entity);
        void dialogActionPutOffTask(int position,TaskDetailEntity entity);
    }
}
