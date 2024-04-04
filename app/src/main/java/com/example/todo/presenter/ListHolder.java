package com.example.todo.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.example.todo.base.PrimaryPresenter;
import com.example.todo.base.PrimaryView;
import com.example.todo.entities.TaskDetailEntity;

import java.util.List;

public interface ListHolder {

    interface View extends PrimaryView {
        Intent intent();
        void updateToolbarTitle(String title);
        void showNoResult();
        void hideResult();
        void updateList(List<TaskDetailEntity> list);
        void startActivityAndForResult(Intent intent, int requestCode);
        void finishActivity();
    }
    interface Presenter extends PrimaryPresenter{
        void onCreate(Bundle saveInstanceState);
        void onDestroy();
        void onItemClick(int position,TaskDetailEntity entity);
    }
}
