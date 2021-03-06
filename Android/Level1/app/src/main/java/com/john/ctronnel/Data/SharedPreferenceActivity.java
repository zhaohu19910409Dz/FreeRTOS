package com.john.ctronnel.Data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.john.ctronnel.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SharedPreferenceActivity extends AppCompatActivity implements View.OnClickListener {

    private final String mFileName = "text.txt";
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
                mEditor.apply();
            }
            break;
            case R.id.show:
            {
                String name = mSharedPreference.getString("name","unknow");
                mTvShow.setText(name);
                mTvShow.setText(read());
            }
            break;
        }
    }

    private void write(String content)
    {
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = openFileOutput(mFileName, MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String read()
    {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(mFileName);
            byte[] buff = new byte[1024];
            StringBuffer stringBuffer = new StringBuffer("");
            int len = 0;
            while((len = fileInputStream.read(buff)) > 0)
            {
                stringBuffer.append(new String(buff, 0, len));
            }
            return  stringBuffer.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}