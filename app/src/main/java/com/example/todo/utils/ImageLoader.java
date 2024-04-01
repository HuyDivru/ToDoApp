package com.example.todo.utils;

import android.view.View;

import androidx.annotation.CallSuper;

public abstract class ImageLoader {

    public static final int FLAG=0x01;
    private static ImageLoader mLoader;
    private int mOptions;

    public static void init(ImageLoader loader){
        mLoader=loader;
    }
    public static ImageLoader get(){
        if(mLoader==null){
            throw new NullPointerException("call imageloader.init() first");
        }
        return mLoader;
    }

    public static void shutdowm() {
        if(mLoader!=null) mLoader.close();
        mLoader=null;
    }

    @CallSuper
    protected void close(){

    }
    public abstract void load(String url, View view);

    public final void load(String uri, View view, int options){
        mOptions=options;
        loadWithOptions(uri,view,options);
    }

    protected void loadWithOptions(String uri, View view, int options) {
        load(uri,view);
    }
    public  static final int OPTION_CENTER_CROP=FLAG<<1;
    public  static final int OPTION_CIRCLE_CROP=FLAG<<2;


    protected final boolean hasOptions(int option){
        return ((mOptions&option)^option)==0;
    }
}
