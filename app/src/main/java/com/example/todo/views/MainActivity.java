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
import android.widget.TextView;

import com.example.todo.R;
import com.example.todo.adapter.MainPageAdapter;
import com.example.todo.base.PrimaryActivity;
import com.example.todo.dagger.ActivityComponent;
import com.example.todo.dagger.ActivityModule;
//import com.example.todo.dagger.DaggerActivityComponent;
import com.example.todo.data.DataDao;
import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.entities.TaskState;
import com.example.todo.fragments.PageFragment;
import com.example.todo.presenter.MainHolder;
import com.example.todo.presenter.MainPresent;
import com.example.todo.utils.SnackBarUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import javax.inject.Inject;
//import com.example.todo.dagger.DaggerActivityComponent;
import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends PrimaryActivity implements MainHolder.View, PageFragment.OnPageFragmentInteractionListener {

    public static final String TAB="MainActivity";
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mVp;
    @BindView(R.id.main_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Inject
    MainPresent mPresenter;
    @Inject
    DataDao mDatDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.bindView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId()  {
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
//        DaggerAcvityComponent.builder()
//                .activityModule(new ActivityModule(this))
//                .build().inject(this);
//        mPresenter.setDataDao(mDatDao);
    }


    @Override
    public int getCurrentViewPageView() {
        return mVp.getCurrentItem();
    }

    @Override
    public void startActivityAndForResult(Intent intent, int requestCode) {
        startActivityForResult(intent,requestCode);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void setViewPagerAdapter(MainPageAdapter adapter) {
        mVp.setAdapter(adapter);
    }

    @Override
    public Intent getActivityIntent() {
        return getIntent();
    }

    @Override
    public void setViewPageCurrentItem(int position, boolean a) {
        mVp.setCurrentItem(position, a);
    }

    @Override
    public void showAction(String message, String action, View.OnClickListener listener) {
        SnackBarUtils.showAction(mCoordinatorLayout,message,action,listener);
    }

    @Override
    public void showDialog(int position, TaskDetailEntity entity) {
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View view=getLayoutInflater().inflate(R.layout.dl_task_item_menu,mCoordinatorLayout,false);

        TextView tvFlagText=(TextView) view.findViewById(R.id.tv_flag_task);

        if(entity.getState()== TaskState.DEFAULT){
            tvFlagText.setText("Mark as completed");
        }
        else{
            tvFlagText.setText("Mark as unCompleted");
        }

        view.findViewById(R.id.ll_action_flag_task).setOnClickListener( v-> {
            bottomSheetDialog.dismiss();
            mPresenter.dialogActionFlagTask(position,entity);
        });
        view.findViewById(R.id.ll_action_edit).setOnClickListener( v-> {
            bottomSheetDialog.dismiss();
            mPresenter.dialogActionEditTask(position,entity);
        });
        view.findViewById(R.id.ll_action_delete).setOnClickListener( v-> {
            bottomSheetDialog.dismiss();
            mPresenter.dialogActionDeleteTask(position,entity);
        });
        view.findViewById(R.id.ll_action_put_off).setOnClickListener( v-> {
            bottomSheetDialog.dismiss();
            mPresenter.dialogActionPutOffTask(position,entity);
        });

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOwnerActivity(this);
        bottomSheetDialog.show();
    }

    @Override
    public void onListTaskItemLongClick(int position, TaskDetailEntity entity) {
        mPresenter.onListTaskItemLongClick(position,entity);
    }

    @Override
    public void onListTaskItemClick(int position, TaskDetailEntity entity) {
        mPresenter.onListTaskItemClick(position,entity);
    }
}