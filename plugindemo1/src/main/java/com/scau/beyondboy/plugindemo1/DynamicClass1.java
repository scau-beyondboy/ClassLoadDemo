package com.scau.beyondboy.plugindemo1;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import intefa.IDynamic;

public class DynamicClass1 implements IDynamic {

    public DynamicClass1() {
       // Log.i(DynamicClass1.class.getName(),"加载动态类");
    }
    public String getTip(){
        return "插件动态加载";
    }

    @Override
    public Drawable getDrawable(Resources resources, @DrawableRes int i) {
        return null;
    }

    @Override
    public String getString(Resources resources) {
        return resources.getString(R.string.test);
    }

    @Override
    public void startPluginActivity(Context context, Class<?> Cls) {
        Intent   intent = new Intent(context,Cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void startPluginActivity(Context context) {
        Intent   intent = new Intent(context,DynamicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public String toString() {
        return "插件";
    }
}
