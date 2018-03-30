package com.bird.gary.bird.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bird.gary.bird.TimeRounder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Gary on 2018/3/30.
 */

public class StartsActivity extends AppCompatActivity{
    //延时时间
    private static final int HOLDTIME = 1500;
    private TimeRounder rounder;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(StartsActivity.this,MainActivity.class));
            StartsActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.sendEmptyMessageDelayed(1,1500);
    }
}
