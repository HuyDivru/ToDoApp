package com.example.todo.data;

import android.text.TextUtils;

import com.example.todo.entities.TaskDetailEntity;
import com.example.todo.entities.TaskState;
import com.example.todo.utils.DateUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;



/**
 * create by honhathuy on 2024/03/29
 */
public class DataDao {
    /*
    volatile is a variable that will be implemented and seen immediately on all other threads
     */
    private static volatile DataDao mDataDao;

    public DataDao() {
    }
    public static DataDao getInstance(){
        if(mDataDao==null){
            synchronized (DataDao.class){
                if(mDataDao==null){
                    mDataDao=new DataDao();
                }
            }
        }
        return mDataDao;
    }

    //function insert task
    /*
        @param taskDetailEntity The TaskDetailEntity object to be inserted
     */
    public void insertTask(TaskDetailEntity taskDetailEntity){
        Realm.getDefaultInstance().executeTransaction(realm -> {
            realm.copyToRealm(taskDetailEntity);
        });
    }
    /*
        function findAllTask
     */
    public RealmResults<TaskDetailEntity> findAllTask(){
            return Realm.getDefaultInstance().where(TaskDetailEntity.class)
                .findAll().sort("timeStamp");
    }
    /*
    function findAllTask(int dayOfWeek)
     */
    public RealmResults<TaskDetailEntity> findAllTask(int dayOfTheWeek){
        return Realm.getDefaultInstance().where(TaskDetailEntity.class)
                .equalTo("dayOfTheWeek",dayOfTheWeek)
                .findAll().sort("timeStamp");
    }
    /*
    function findUnFiinshedTask(int dayOfTheWeek)
     */
    public RealmResults<TaskDetailEntity> findUnFinished(int dayOfTheWeek){
        return findUnFinished(dayOfTheWeek,0);
    }

    public RealmResults<TaskDetailEntity> findUnFinished(int dayOfTheWeek, long since) {
        return Realm.getDefaultInstance().where(TaskDetailEntity.class)
                .equalTo("dayOfTheWeek",dayOfTheWeek)
                .notEqualTo("state", TaskState.FINISHED)
                .greaterThanOrEqualTo("timeStamp",since)
                .findAll().sort("timeStamp");
    }
    /*
    function findAllTaskofThisWeekfromMonday
     */
    public RealmResults<TaskDetailEntity> findAllTaskOfThisWeekFromMonday(){
        long sundayTimeMillisOfWeek= DateUtils.getFirstModayTimeMillisOfWeek();

        return Realm.getDefaultInstance().where(TaskDetailEntity.class)
                .greaterThan("timeStamp",sundayTimeMillisOfWeek)
                .findAll()
                ;
    }
    /*
    function edittask
     */
    public void editTask(TaskDetailEntity oldTask,TaskDetailEntity newTask){
        Realm.getDefaultInstance().executeTransaction(realm-> oldTask.setTaskDetailEntity(newTask));
    }
    /*
    function delete
     */
    public void deleteTask(TaskDetailEntity taskDetailEntity){
        Realm.getDefaultInstance().executeTransaction(realm -> {
            TaskDetailEntity first=realm.where(TaskDetailEntity.class)
                    .equalTo("dayOfTheWeek",taskDetailEntity.getDayOfTheWeek())
                    .equalTo("title",taskDetailEntity.getTitle())
                    .equalTo("icon",taskDetailEntity.getIcon())
                    .equalTo("priority",taskDetailEntity.getPriority())
                    .equalTo("state",taskDetailEntity.getState())
                    .equalTo("timeStamp",taskDetailEntity.getTimeStamp())
                    .equalTo("content",taskDetailEntity.getContent())
                    .findFirst();

            if(first!=null){
                first.deleteFromRealm();
            }
        });
    }
    /*
    function search task
     */
    public List<TaskDetailEntity> searchTask(String task){
        if(TextUtils.isEmpty(task)) throw new IllegalArgumentException("String is null");
        return Realm.getDefaultInstance().where(TaskDetailEntity.class)
                .contains("content",task)
                .or()
                .contains("title",task)
                .findAll().sort("timeStamp", Sort.DESCENDING);
    }


    //function switchTaskState
    public void switchTaskState(TaskDetailEntity entity,int state){
        Realm.getDefaultInstance().executeTransaction(realm -> entity.setState(state));
    }

    public void close(){
        mDataDao=null;
    }
}
