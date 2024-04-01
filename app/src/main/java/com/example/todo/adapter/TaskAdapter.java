package com.example.todo.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.data.ImageFactory;
import com.example.todo.entities.TaskDetailEntity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private Context context;
    private List<TaskDetailEntity> mList;
    private boolean showPriority=true;
    private final int[] mPriorityIcons;

    public void setShowPriority(boolean showPriority) {
        this.showPriority = showPriority;
    }
    public boolean isShowPriority() {
        return showPriority;
    }
    public TaskAdapter(Context context, List<TaskDetailEntity> mList) {
        this.context = context;
        this.mList = mList;
        mPriorityIcons = ImageFactory.createIcons();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ta,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @NonNull


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
