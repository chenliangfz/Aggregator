package com.aggregator.card.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aggregator.card.R;

/**
 * Created by ChenLiang on 16/11/13.
 */

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
                finish();
            }
        },2*1000L);
    }
}
