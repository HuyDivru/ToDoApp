package com.example.todo.presenter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.todo.base.PrimaryView;
import com.example.todo.constant.Contants;
import com.example.todo.data.DataDao;
import com.example.todo.data.InstrumentHelper;
import com.example.todo.entities.TaskDetailEntity;

import java.util.List;

import javax.inject.Inject;

public class ListPresent implements ListHolder.Presenter{
    public static final String TAG = "ListPresent";
    private Context mContext;
    private ListHolder.View mView;

    @Inject
    DataDao mDataDao;
    @Inject
    public ListPresent(Context context){
        mContext = context;
    }
    @Override
    public void bindView(PrimaryView view) {
        mView=(ListHolder.View) view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
       handleIntent(mView.intent());
    }

    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query=intent.getStringExtra(SearchManager.QUERY);
            if(TextUtils.isEmpty(query)){
                return;
            }
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        List<TaskDetailEntity> list=mDataDao.searchTask(query);
        int size=list.size();
        mView.hideResult();
        if(size==0){
            mView.updateToolbarTitle("No result found");
            mView.showNoResult();
            return;
        }
        mView.updateToolbarTitle("Found" + size + "results");
        mView.updateList(list);
        Log.d(TAG,"doSearch: "+list);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onItemClick(int position, TaskDetailEntity entity) {
        Intent intent= InstrumentHelper.toEditActivity(mContext,position,entity);
        mView.finishActivity();
        mView.startActivityAndForResult(intent, Contants.EDIT_ACTIVITY_REQUEST_CODE);
    }
}
