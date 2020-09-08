package com.john.ctronnel.Data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.R;

public class DataStorageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_sharedpreference, btn_file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);

        btn_sharedpreference = (Button)findViewById(R.id.btn_sharedpreference);
        btn_sharedpreference.setOnClickListener(this);

        btn_file = (Button)findViewById(R.id.btn_file);
        btn_file.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId())
        {
            case R.id.btn_sharedpreference:
                intent = new Intent(DataStorageActivity.this, SharedPreferenceActivity.class);
                break;
            case R.id.btn_file:
                intent = new Intent(DataStorageActivity.this, FileActivity.class);
                break;
        }

        startActivity(intent);
    }
}