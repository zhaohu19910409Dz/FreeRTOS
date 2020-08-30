package com.john.ctronnel.recycleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.john.ctronnel.R;
import com.john.ctronnel.gridview.GridViewActivity;

public class GridRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView gridRecylerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler_view);

        gridRecylerView = (RecyclerView)findViewById(R.id.gridRecyclerView);
        GridLayoutManager layout = new GridLayoutManager(GridRecyclerViewActivity.this, 3);
        gridRecylerView.setLayoutManager(layout);
        gridRecylerView.setAdapter(new GridAdapter(GridRecyclerViewActivity.this, new GridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(GridRecyclerViewActivity.this, "click"+pos, Toast.LENGTH_LONG).show();
            }
        }));


    }
}
