package com.john.ctronnel.recycleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.john.ctronnel.R;

public class LinearRecycleViewActivity extends AppCompatActivity {

    private RecyclerView mRvmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_recycle_view);

        mRvmain = (RecyclerView) findViewById(R.id.rv_main);
        mRvmain.setLayoutManager(new LinearLayoutManager(LinearRecycleViewActivity.this));
        mRvmain.setAdapter(new LinearAdapter(LinearRecycleViewActivity.this));
    }
}
