package com.john.ctronnel.gridview;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.john.ctronnel.R;
import com.john.ctronnel.listView.MyListAdapter;

public class GridViewActivity extends AppCompatActivity {

    private GridView mGv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        mGv = (GridView)findViewById(R.id.gv);
        mGv.setAdapter(new MyListAdapter(GridViewActivity.this));
    }
}
