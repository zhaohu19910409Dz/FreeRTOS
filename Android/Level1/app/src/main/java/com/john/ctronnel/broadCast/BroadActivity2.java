package com.john.ctronnel.broadCast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.R;

public class BroadActivity2 extends AppCompatActivity {

    private Button mBtn1;
    private MyBroadcastReceiver recevier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad2);

        recevier = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("john.zhao.info");
        registerReceiver(recevier, filter);

        mBtn1 = (Button)findViewById(R.id.btn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("john.zhao.info");
                Bundle bundle = new Bundle();
                bundle.putString("Title", "I am BroadActivity2");
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }
}