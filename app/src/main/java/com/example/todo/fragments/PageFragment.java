package com.example.todo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todo.R;
import com.example.todo.adapter.TaskAdapter;
import com.example.todo.databinding.FragmentPageBinding;
import com.example.todo.entities.TaskDetailEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PageFragment extends Fragment {

    //
    public static final String TAG = "PageFragment";
    private List<TaskDetailEntity> mList=new ArrayList<>();
    private TaskAdapter mAdapter;
   @BindView(R.id.rv)
    RecyclerView mRv;
    private  OnPageFragmentInteractionListener mListener;


    public PageFragment(){
        Log.d("Pagefragment","constructor");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initViews();
    }

    private void initViews() {
        mAdapter=new TaskAdapter(getContext(),mList);
//        boolean showPriority=PreferceUtils;
    }
    //insert task
    public void insertTask(TaskDetailEntity task) {
        if(!mList.contains(task)){
            mList.add(task);
        }
        if(mAdapter!=null){
            mAdapter.notifyItemInserted(mList.size()-1);
        }
    }

    public TaskDetailEntity deleteTask(int index) {
        TaskDetailEntity taskDetailEntity=mList.get(index);
        mList.remove(index);
        mAdapter.notifyItemRemoved(index);
        return taskDetailEntity;
    }


    public void clearTasks() {
        mList.clear();
    }

    public interface OnPageFragmentInteractionListener {
        void onListTaskItemLongClick(int position, TaskDetailEntity entity);
        void onListTaskItemClick(int position, TaskDetailEntity entity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener=null;
        mAdapter=null;
        mRv=null;
    }
}