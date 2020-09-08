package com.john.ctronnel.broadCast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.john.ctronnel.R;

public class BroadActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mTx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad);

        mBtn = (Button)findViewById(R.id.btn1);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BroadActivity.this, BroadActivity2.class);
                startActivity(intent);
            }
        });

        mTx = (TextView)findViewById(R.id.tv_test);

    }
}