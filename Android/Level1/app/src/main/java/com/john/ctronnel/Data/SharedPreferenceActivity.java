package com.john.ctronnel.Data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.john.ctronnel.R;

public class SharedPreferenceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName;
    private Button mSaveBtn, mShowBtn;
    private TextView mTvShow;

    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        mName = (EditText)findViewById(R.id.name);
        mSaveBtn = (Button)findViewById(R.id.save);
        mShowBtn = (Button)findViewById(R.id.show);
        mTvShow = (TextView)findViewById(R.id.tv_show);

        mSaveBtn.setOnClickListener(this);
        mShowBtn.setOnClickListener(this);

        mSharedPreference = getSharedPreferences("data", MODE_PRIVATE);
        mEditor = mSharedPreference.edit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.save:
            {
                mEditor.putString("name", mName.getText().toString());
                //mEditor.commit();
                mEditor.apply();
            }
            break;
            case R.id.show:
            {
                String name = mSharedPreference.getString("name","unknow");
                mTvShow.setText(name);
            }
            break;
        }
    }
}