package com.john.ctronnel.recycleView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.R;
import com.john.ctronnel.gridview.GridViewActivity;

public class recycleViewActivity extends AppCompatActivity {

    private Button mBtnLinear;
    private Button mBtnHor;
    private Button mBtnGrid;
    private Button mBtnPu;

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

        mBtnHor = (Button)findViewById(R.id.btn_hor);
        mBtnHor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(recycleViewActivity.this, HorRecyclerViewActivity.class);
                startActivity(inten);
            }
        });

        mBtnGrid = (Button)findViewById(R.id.btn_gird);
        mBtnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(recycleViewActivity.this, GridRecyclerViewActivity.class);
                startActivity(inten);
            }
        });

        mBtnPu = (Button)findViewById(R.id.btn_pu);
        mBtnPu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(recycleViewActivity.this, PuRecyclerViewActivity.class);
                startActivity(inten);
            }
        });
    }
}
