package com.john.ctronnel;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LifeActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.i("LifeActiviy", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LifeActiviy", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LifeActiviy", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeActiviy", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeActiviy", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LifeActiviy", "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeActiviy", "onStop");
    }
}
