package com.john.ctronnel.jump;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.john.ctronnel.R;
import com.john.ctronnel.utils.ToastUtil;

public class AActivity extends AppCompatActivity {

    private Button jumpBtn, jumpBtn1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a);

        Log.d("AActivity", "OnCreate");
        Log.d("AActivity", "tashID:"+getTaskId() + " ,hash:" + hashCode());
        logTashName();
        jumpBtn = (Button)findViewById(R.id.jumpBtn);
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inent = new Intent(AActivity.this, BActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name", "John");
                bundle.putInt("Age", 29);
                inent.putExtras(bundle);
                //startActivity(inent);
                startActivityForResult(inent,0);

                //Intent intent = new Intent();
                //intent.setClass(AActivity.this, BActivity.class);
                //startActivity(intent);

                //Intent intent = new Intent();
                //intent.setClassName(AActivity.this, "com.john.ctronnel.jump.BActivity");
                //startActivity(intent);

                //Intent intent = new Intent();
                //intent.setComponent(new ComponentName(AActivity.this, "com.john.ctronnel.jump.BActivity"));
                //startActivity(intent);

                //Intent intent = new Intent();
                //intent.setAction("com.john.zhao.BActivity");
                //startActivity(intent);
            }
        });

        jumpBtn1 = (Button)findViewById(R.id.jumpBtn1);
        jumpBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AActivity.this, AActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtil.showMsg(AActivity.this, data.getExtras().getString("Title"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("AActivity", "onNewIntent");
        Log.d("AActivity", "tashID:"+getTaskId() + " ,hash:" + hashCode());
        logTashName();
    }

    private void logTashName()
    {
        try
        {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Log.d("AActivity",info.taskAffinity);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
