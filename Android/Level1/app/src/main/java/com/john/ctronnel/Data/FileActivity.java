package com.john.ctronnel.Data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.john.ctronnel.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileActivity extends AppCompatActivity implements View.OnClickListener{

    private final String mFileName = "text.txt";
    private EditText mName;
    private Button mSaveBtn, mShowBtn;
    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        mName = (EditText)findViewById(R.id.name);
        mSaveBtn = (Button)findViewById(R.id.save);
        mShowBtn = (Button)findViewById(R.id.show);
        mTvShow = (TextView)findViewById(R.id.tv_show);

        mSaveBtn.setOnClickListener(this);
        mShowBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.save:
            {
                write(mName.getText().toString());
            }
            break;
            case R.id.show:
            {
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
            //fileOutputStream = openFileOutput(mFileName, MODE_PRIVATE);
            File dir = new File(Environment.getExternalStorageDirectory(), "John");
            if(!dir.exists())
            {
                dir.mkdirs();
            }

            File file = new File(dir, mFileName);
            if(!file.exists())
            {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);

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
            //fileInputStream = openFileInput(mFileName);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"John", mFileName);
            fileInputStream = new FileInputStream(file);
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