package com.john.ctronnel.recycleView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.john.ctronnel.R;

public class HorRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rvHor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hor_recycler_view);

        rvHor = (RecyclerView)findViewById(R.id.rv_hor);
        LinearLayoutManager linearLayout = new LinearLayoutManager(HorRecyclerViewActivity.this);
        linearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHor.setLayoutManager(linearLayout);
        rvHor.addItemDecoration(new MyItemDecoration());
        rvHor.setAdapter(new HorAdapter(HorRecyclerViewActivity.this, new HorAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(HorRecyclerViewActivity.this, "click"+pos, Toast.LENGTH_LONG).show();
            }
        }));
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, getResources().getDimensionPixelOffset(R.dimen.dividerHeight), 0);
        }
    }
}
