package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.jump.AActivity;

import com.john.ctronnel.fragment.ContinerActivity;
import com.john.ctronnel.widget.MyButton;

public class MainActivity extends AppCompatActivity {

    private Button UIActivity;
    private Button ActivityBtn;
    private Button JumpActivity1;
    private Button JumpActivity2;
    private Button FragmentActivity;
    private MyButton myBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UIActivity = (Button)findViewById(R.id.bt1);
        ActivityBtn = (Button)findViewById(R.id.bt2);
        JumpActivity1 = (Button)findViewById(R.id.bt3);
        JumpActivity2 = (Button)findViewById(R.id.bt4);
        FragmentActivity = (Button)findViewById(R.id.bt5);
        myBtn = (MyButton)findViewById(R.id.myBtn);

        setListeners();

        myBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("MainActivity", "匿名类监听");
                return false;
            }
        });
    }

    private void setListeners()
    {
        OnClick onClick = new OnClick();
        UIActivity.setOnClickListener(onClick);
        ActivityBtn.setOnClickListener(onClick);
        JumpActivity1.setOnClickListener(onClick);
        JumpActivity2.setOnClickListener(onClick);
        FragmentActivity.setOnClickListener(onClick);
        myBtn.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        Intent intent = null;
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bt1:
                {
                    intent = new Intent(MainActivity.this, UIActivity.class);
                    break;
                }
                case R.id.bt2:
                {
                    intent = new Intent(MainActivity.this, LifeActiviy.class);
                    break;
                }
                case R.id.bt3:
                {
                    intent = new Intent(MainActivity.this, AActivity.class);
                    break;
                }
                case R.id.bt4:
                {
                    break;
                }
                case R.id.bt5:
                {
                    intent = new Intent(MainActivity.this, ContinerActivity.class);
                    break;
                }
            }

            if(intent != null)
            {
                startActivity(intent);
            }
        }
    }
}
