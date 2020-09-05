package com.john.ctronnel.jump;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.john.ctronnel.R;
import com.john.ctronnel.utils.ToastUtil;

public class AActivity extends AppCompatActivity {

    private Button jumpBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtil.showMsg(AActivity.this, data.getExtras().getString("Title"));
    }
}
