package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button UIActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UIActivity = (Button)findViewById(R.id.bt1);
        setListeners();
    }

    private void setListeners()
    {
        OnClick onClick = new OnClick();
        UIActivity.setOnClickListener(onClick);
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
            }
            startActivity(intent);
        }
    }
}
