package com.john.ctronnel.jump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.john.ctronnel.R;

public class BActivity extends AppCompatActivity {

    private TextView tx;
    private Button btnFinish;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_b);

        tx = (TextView)findViewById(R.id.tv_title);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("Name");
        int age = bundle.getInt("Age");

        tx.setText(name + "," + age);

        btnFinish = (Button)findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("Title", "I am back");
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
