package com.scau.beyondboy.plugindemo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DynamicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(DynamicActivity.class.getName(),"DynamicActivity has started");
        setContentView(R.layout.activity_main);
    }
}
