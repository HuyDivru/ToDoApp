package com.example.todo.dagger;

import com.example.todo.entities.TaskDetailEntity;

import dagger.Module;
import dagger.Provides;

@Module
public class EntityModule {
    @Provides
    public TaskDetailEntity provideTaskDetailEntity(){
        return new TaskDetailEntity();
    }
}
