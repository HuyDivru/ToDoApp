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
import com.tencent.bugly.crashreport.CrashReport;

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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        Context context = getApplicationContext();
        //get the current package name
        String packageName = context.getPackageName();
        //get the current process name
        String processName = getProcessName(Process.myPid());

        //set whether it is a reporting process
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel("ToDoApp");

        File file = null;
        try {
            file = new File(Contants.DATABASE_FILE_PATH_FOLDER);
            file.mkdirs();
        } catch (Exception e) {

        }

        Realm.init(this);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder().name(Contants.DATABASE_FILE_PATH_FILE_NAME);

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && file != null) {
                builder.directory(file);
            } else {
                builder.directory(new File(Contants.ExternalStorageDirectory + Contants.DATABASE_FILE_PATH_FOLDER));
            }
        } catch (Exception e) {
            Toast.makeText(context, "In order to get a better user experience , please grant relevan permissions to this application in the settings", Toast.LENGTH_SHORT).show();
        }

        Realm.setDefaultConfiguration(builder.build());
        ImageLoader.init(GlideImageLoader.create(this));
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
