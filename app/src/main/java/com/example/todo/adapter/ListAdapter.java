package com.example.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.entities.TaskState;
import com.example.todo.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<TaskDetailEntity> mList=new ArrayList<>();
    @Inject
    public ListAdapter(){

    }
    public void setList(List<TaskDetailEntity> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder) holder;
        viewHolder.setView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_state)
        TextView mTvState;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
        public void setView(TaskDetailEntity entity){
            mTvTitle.setText(entity.getTitle());
            mTvContent.setText(entity.getContent());
            mTvState.setText(entity.getState());
            long timeStamp=entity.getTimeStamp();
            String date= DateUtils.formatDateWeek(timeStamp);
            mTvDate.setText(date);
            int state=entity.getState();
            String stateStr=(state== TaskState.FINISHED)?"Completed":"UnCompleted";
            mTvState.setText(stateStr);
        }
    }
}
