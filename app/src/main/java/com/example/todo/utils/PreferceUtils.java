package com.example.todo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferceUtils {
    private static final String PREFERENCE_FILE_NAME = "config";
    private SharedPreferences   sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

        private static volatile PreferceUtils preferceUtils=null;
    protected PreferceUtils(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedEditor = sharedPreferences.edit();
    }

    public static PreferceUtils getInstance(Context context){
        if(preferceUtils==null){
            synchronized (PreferceUtils.class){
                if(preferceUtils==null){
                    preferceUtils=new PreferceUtils(context.getApplicationContext());
                }
            }
        }
        return  preferceUtils;
    }
    public String getStringParam(String key){
        return sharedPreferences.getString(key,"");
    }
    public String getStringParam(String key,String defaultString){
     return sharedPreferences.getString(key,defaultString);
    }
    public void saveParam(String key,String value){
        sharedEditor.putString(key,value).apply();
    }
    public boolean getBooleanParam(String key){
        return sharedPreferences.getBoolean(key,false);
    }
    public boolean getBooleanParam(String key,boolean defaultBoolean){
        return sharedPreferences.getBoolean(key,defaultBoolean);
    }
    public void saveParam(String key,boolean value){
        sharedEditor.putBoolean(key,value).apply();
    }
    public int getIntParam(String key){
        return sharedPreferences.getInt(key,0);
    }
    public void saveParam(String key,int value){
        sharedEditor.putInt(key,value).apply();
    }
    public long getLongParam(String key){
        return sharedPreferences.getLong(key,0);
    }
    public long getLongParam(String key,long defaultLong){
        return sharedPreferences.getLong(key,defaultLong);
    }
    public void saveParam(String key,long value){
        sharedEditor.putLong(key,value).apply();
    }
    public void removeKey(String key){
        sharedEditor.remove(key).apply();
    }
}
