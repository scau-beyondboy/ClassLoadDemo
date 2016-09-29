package com.scau.beyondboy.plugindemo1;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {
    public static  Context scontext;
    @Override
    public void onCreate() {
        super.onCreate();
        scontext=this;
        Log.i(MyApplication.class.getName(),"插件初始化");
    }
}
