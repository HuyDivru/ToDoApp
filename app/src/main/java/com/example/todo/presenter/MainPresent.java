package com.example.todo.presenter;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.todo.R;
import com.example.todo.adapter.MainPageAdapter;
import com.example.todo.base.PrimaryView;
import com.example.todo.constant.Contants;
import com.example.todo.data.DataDao;
import com.example.todo.data.InstrumentHelper;
import com.example.todo.entities.MainPageItem;
import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.entities.TaskState;
import com.example.todo.fragments.PageFragment;
import com.example.todo.utils.DateUtils;
import com.example.todo.utils.PreferceUtils;
import com.example.todo.views.NewActivity;
import com.example.todo.views.SettingActivity;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;


import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

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

//        for (int i= 0; i <7; i++) {
//            ((PageFragment) mItems.get(i).getFragment()).clearTasks();
//        }
        RealmResults<TaskDetailEntity> allTaks=null;
        if(PreferceUtils.getInstance(mContext).getBooleanParam(Contants.CONFIG_KEY.SHOW_WEEK_TASK)){
            allTaks=mDataDao.findAllTaskOfThisWeekFromMonday();
        }
        else{
            allTaks=mDataDao.findAllTask();
        }
        for(TaskDetailEntity task:allTaks) {
          int day= task.getDayOfTheWeek();
          PageFragment fragment=(PageFragment) mItems.get(day-1).getFragment();
          fragment.insertTask(task);
      }

      //night mode
        if(PreferceUtils.getInstance(mContext).getBooleanParam(Contants.CONFIG_KEY.AUTO_SWITCH_NIGHT_MODE,true)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME);
        }
        else {
            boolean isNightMode=PreferceUtils.getInstance(mContext).getBooleanParam(Contants.CONFIG_KEY.NIGHT_MODE,false);
            if(isNightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

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
    public void dialogActionFlagTask(int position, TaskDetailEntity entity) {
        int newState= TaskState.DEFAULT;
        if(entity.getState()==TaskState.DEFAULT){
            newState=TaskState.FINISHED;
        }
        else {
            newState=TaskState.DEFAULT;
        }
        switchTaskState(position,entity,newState);
    }

    @Override
    public void dialogActionEditTask(int position, TaskDetailEntity entity) {
        toEditActivity(position,entity);
    }

    private void switchTaskState(int position, TaskDetailEntity entity, int newState) {
        PageFragment fragment=(PageFragment) mItems.get(mV.getCurrentViewPageView()).getFragment();
        mDataDao.switchTaskState(entity,newState);
        fragment.getAdapter().notifyItemChanged(position);
    }


    @Override
    public void dialogActionDeleteTask(int position, TaskDetailEntity entity) {
        Disposable disposable=deleteTaskWithDelay(position,entity);
        mV.showAction("About to be deleted","Cancel",view -> disposable.dispose());
    }

    private Disposable deleteTaskWithDelay(int position, TaskDetailEntity entity) {
      return Observable.just(1).delay(2, TimeUnit.SECONDS)
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeOn(Schedulers.io())
              .subscribe(integer ->{deleteTask(position,entity);});
    }

    private void deleteTask(int position, TaskDetailEntity entity) {
        PageFragment fragment=(PageFragment) mItems
                .get(mV.getCurrentViewPageView()).getFragment();
        DataDao dao=DataDao.getInstance();
        fragment.deleteTask(position);
        dao.deleteTask(entity);
    }
    @Override
    public void dialogActionPutOffTask(int position, TaskDetailEntity entity) {
        Disposable disposable=putOffTaskOneDayWithDelay(position,entity);
        mV.showAction("The task will be postponed for one day","Cancel",view -> disposable.dispose());
    }

    private Disposable putOffTaskOneDayWithDelay(int position, TaskDetailEntity entity) {
        return Observable.just(1).delay(2,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(integer -> {putOffTaskOneDay(position,entity);});

    }

    private void putOffTaskOneDay(int position, TaskDetailEntity entity) {
        PageFragment fragment=(PageFragment) mItems.get(mV.getCurrentViewPageView()).getFragment();
        TaskDetailEntity oldEntity=fragment.deleteTask(position);
        TaskDetailEntity newEntity=oldEntity.cloneObj();
        newEntity.setDayOfTheWeek(DateUtils.callNextDayOfWeek(oldEntity.getDayOfTheWeek()));
        ((PageFragment) mItems.get(newEntity.getDayOfTheWeek()-1).getFragment()).insertTask(newEntity);
        mDataDao.insertTask(newEntity);
        mDataDao.deleteTask(oldEntity);
    }

    private void toEditActivity(int position, TaskDetailEntity entity) {
        Intent intent= InstrumentHelper.toEditActivity(mContext,position,entity);
        mV.startActivityAndForResult(intent,Contants.EDIT_ACTIVITY_REQUEST_CODE);
        mLastClickPosition=position;
    }

    public void setDataDao(DataDao mDatDao) {
        this.mDataDao=mDatDao;
    }
}
