package com.john.ctronnel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.john.ctronnel.utils.ToastUtil;

public class HandlerActivity extends AppCompatActivity {

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

//        mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(HandlerActivity.this, UIActivity.class);
//                startActivity(intent);
//            }
//        }, 3000);

        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 1:
                    {
                        ToastUtil.showMsg(HandlerActivity.this, "Receviced Message!!!");
                    }
                    break;
                }
            }
        };

        new Thread()
        {
            @Override
            public void run() {
                super.run();
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }.start();
    }
}