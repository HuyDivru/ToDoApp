package com.example.todo.presenter;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.todo.R;
import com.example.todo.adapter.MainPageAdapter;
import com.example.todo.base.PrimaryView;
import com.example.todo.constant.Contants;
import com.example.todo.data.DataDao;
import com.example.todo.data.InstrumentHelper;
import com.example.todo.entities.MainPageItem;
import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.fragments.PageFragment;
import com.example.todo.views.NewActivity;
import com.example.todo.views.SettingActivity;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class MainPresent implements MainHolder.Presenter{
    public static final String TAG = "MainPresent";
    private MainHolder.View mV;
    private Context  mContext;
    private int mLastClickPosition ;

    @Inject
    List<MainPageItem> mItems;
    @Inject
    MainPageAdapter mainPageAdapter;
    @Inject
    DataDao mDataDao;

    @Inject
    public MainPresent(Context context){
        mContext = context;
    }


    @Override
    public void bindView(PrimaryView view) {
        mV=(MainHolder.View) view;
    }

    @Override
    public void onBackPressed() {
        mV.finishActivity();
    }

    @Override
    public void onFabClick() {
        int i=mV.getCurrentViewPageView();
        Intent intent=new Intent(mContext, NewActivity.class);
        intent.putExtra(Contants.INTENT_EXTRA_DAY_OF_WEEK,i+1);
        intent.putExtra(Contants.INTENT_EXTRA_MODE_OF_NEW_ACT,Contants.MODE_OF_NEW_ACT.MODE_CREATE);
        mV.startActivityAndForResult(intent,Contants.NEW_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent=new Intent(mContext, SettingActivity.class);
                intent.putExtra(Contants.INTENT_EXTRA_SWITCH_TO_INDEX,mV.getCurrentViewPageView());
                mContext.startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mV.setViewPagerAdapter(mainPageAdapter);

        int currentIndex;
        int dayOfTheWeek= Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        currentIndex=dayOfTheWeek-1;
        Intent intent=mV.getActivityIntent();
        if(intent!=null){
            currentIndex=intent.getIntExtra(Contants.INTENT_EXTRA_SWITCH_TO_INDEX,currentIndex);
        }
        mV.setViewPageCurrentItem(currentIndex,true);

        for (int i= 0; i <7; i++) {
            ((PageFragment) mItems.get(i).getFragment()).clearTasks();
        }
//
    }

    @Override
    public void onDestroy() {
        mItems=null;
        mDataDao.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK) return;
        Bundle bundle = data.getExtras();
        TaskDetailEntity task=(TaskDetailEntity) bundle.getSerializable(Contants.INTENT_BUNDLE_NEW_TASK_DETAIL);
        PageFragment fragment=(PageFragment) mItems.get(mV.getCurrentViewPageView()).getFragment();
        if(requestCode==Contants.NEW_ACTIVITY_REQUEST_CODE) {
            mDataDao.insertTask(task);
            fragment.insertTask(task);

        }
        else if(requestCode==Contants.EDIT_ACTIVITY_REQUEST_CODE){
            TaskDetailEntity oldTask=fragment.deleteTask(mLastClickPosition);
            fragment.insertTask(task);
            mDataDao.editTask(oldTask,task);
        }

    }

    @Override
    public void onListTaskItemLongClick(int position, TaskDetailEntity entity) {
        mV.showDialog(position,entity);
    }

    @Override
    public void onListTaskItemClick(int position, TaskDetailEntity entity) {
        toEditActivity(position,entity);
    }

    @Override
    public void dialogActionItemClick(int position, TaskDetailEntity entity) {

    }

    @Override
    public void dialogActionDeleteTask(int position, TaskDetailEntity entity) {

    }

    @Override
    public void dialogActionPutOffTask(int position, TaskDetailEntity entity) {

    }

    private void toEditActivity(int position, TaskDetailEntity entity) {
        Intent intent= InstrumentHelper.toEditActivity(mContext,position,entity);
        mV.startActivityAndForResult(intent,Contants.EDIT_ACTIVITY_REQUEST_CODE);
        mLastClickPosition=position;
    }

}
