package com.example.todo.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * create by honhathuy on 2024/03/29
 */
public class TaskDetailEntity  extends RealmObject implements Serializable {

    public static final long serialVersionUID=1L;
    private int dayOfTheWeek;
    private long timeStamp;
    private int state;
    private int priority;
    private String title;
    private String content;
    private String icon;


    public TaskDetailEntity() {
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTaskDetailEntity(TaskDetailEntity newTask) {
        this.dayOfTheWeek = newTask.getDayOfTheWeek();
        this.timeStamp = newTask.getTimeStamp();
        this.state = newTask.getState();
        this.priority = newTask.getPriority();
        this.title = newTask.getTitle();
        this.content = newTask.getContent();
        this.icon = newTask.getIcon();
    }
    public TaskDetailEntity cloneObj(){
        return null;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj==null) return false;
        if(obj==this) return true;

        if(!(obj instanceof TaskDetailEntity)) return false;
        TaskDetailEntity other=(TaskDetailEntity) obj;
        if((other.title==title || other.title.equals(title))
            && (other.content==content || other.content.equals(content))
            && (other.icon==icon || other.icon.equals(icon))
                && (other.dayOfTheWeek==dayOfTheWeek)
                &&(other.priority==priority)
        )
            return true;
        return false;
    }
    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
