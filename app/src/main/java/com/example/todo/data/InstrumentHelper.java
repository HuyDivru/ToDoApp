package com.example.todo.data;

import android.content.Context;
import android.content.Intent;

import com.example.todo.constant.Contants;
import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.views.NewActivity;

public class InstrumentHelper {
    private InstrumentHelper(){

    }
    //Intent toEditActivity
    public static Intent toEditActivity(Context context, int position, TaskDetailEntity entity){
        Intent intent=new Intent(context, NewActivity.class);
        intent.putExtra(Contants.INTENT_EXTRA_EDIT_TASK_DETAIL_ENTITY,entity.cloneObj());
        intent.putExtra(Contants.INTENT_EXTRA_MODE_OF_NEW_ACT,Contants.MODE_OF_NEW_ACT.MODE_EDIT);
        return intent;
    }
}
