package com.example.todo;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.todo.constant.Contants;
import com.example.todo.utils.GlideImageLoader;
import com.example.todo.utils.ImageLoader;
import com.squareup.leakcanary.LeakCanary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class ToDoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize LeakCanary to detect memory leaks
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        // Initialize Realm
        Realm.init(this);
        try {
            // Define the Realm configuration
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name(Contants.DATABASE_FILE_PATH_FILE_NAME)
                    // Uncomment and complete if migrating the Realm schema
                    // .migration(new MyRealmMigration())
                    .build();

            // Set the default Realm configuration
            Realm.setDefaultConfiguration(config);

            // Initialize your image loader
            ImageLoader.init(GlideImageLoader.create(this));

        } catch (Exception e) {
            // Proper exception handling here, consider notifying the user or logging the issue
        }
    }

    /*
    create 29/03/2024
        get the process name corresponding to the process number
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if(!TextUtils.isEmpty(processName)){
                processName= processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ImageLoader.shutdowm();
    }
}
