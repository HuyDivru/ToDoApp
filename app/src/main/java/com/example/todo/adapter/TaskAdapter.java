package com.example.todo.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.data.ImageFactory;
import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.entities.TaskState;
import com.example.todo.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private Context context;
    private List<TaskDetailEntity> mList;
    private boolean showPriority=true;
    private final int[] mPriorityIcons;
    private OnItemClickListener mListener;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.task_item,parent,false));
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder) holder;
        TaskDetailEntity entity=mList.get(position);
        viewHolder.setEntity(entity);
    }

    @NonNull
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TaskDetailEntity entity;
        @BindView(R.id.tv_title)
        TextView mvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.sdv_icon)
        ImageView mIvIcon;
        @BindView(R.id.iv_cur_priority)
        ImageView mIvCurrPriority;
        @BindView(R.id.ll_task_finished_mask)
        LinearLayout mLinearFinishedMask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener( v -> mListener.onItemClick(getAdapterPosition(),entity));
            itemView.setOnLongClickListener(v->{
                mListener.onItemLongClick(getAdapterPosition(),entity);
                return true;
            });
        }

        void setEntity(TaskDetailEntity entity){
            this.entity=entity;
            //title
            mvTitle.setText(entity.getTitle());
            //content
            String content=entity.getContent();
            int length=content.length();
            String showContent=content.substring(0,Math.min(length,25));
            if(length>=25){
                showContent+="...";
            }
            mTvContent.setText(showContent);
            //icon
            ImageLoader.get().load(entity.getIcon(),mIvIcon,ImageLoader.OPTION_CENTER_CROP|ImageLoader.OPTION_CIRCLE_CROP);
            //priority
            if(showPriority){
                if(!mIvCurrPriority.isShown()){
                    mIvCurrPriority.setVisibility(View.VISIBLE);
                    int priority=entity.getPriority();
                    mIvCurrPriority.setImageResource(mPriorityIcons[priority]);
                }
                else {
                    if(mIvCurrPriority.isShown()){
                        mIvCurrPriority.setVisibility(View.INVISIBLE);
                    }
                }
            }
            //isfinished
            int state=entity.getState();
            if(state== TaskState.FINISHED){
                mLinearFinishedMask.setVisibility(View.VISIBLE);
            }
            else {
                mLinearFinishedMask.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position,TaskDetailEntity entity);
        void  onItemLongClick(int position,TaskDetailEntity entity);
    }

}
