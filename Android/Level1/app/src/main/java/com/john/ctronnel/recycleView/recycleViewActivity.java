package com.john.ctronnel.recycleView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.R;

public class recycleViewActivity extends AppCompatActivity {

    private Button mBtnLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        mBtnLinear = (Button) findViewById(R.id.btn_linear);
        mBtnLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(recycleViewActivity.this, LinearRecycleViewActivity.class);
                startActivity(inten);
            }
        });
    }
}
