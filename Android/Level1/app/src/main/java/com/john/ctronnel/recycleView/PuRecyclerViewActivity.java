package com.john.ctronnel.recycleView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.john.ctronnel.R;

public class PuRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rv_pu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pu_recycler_view);

        rv_pu = (RecyclerView)findViewById(R.id.rv_pu);
        rv_pu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_pu.setAdapter(new StaggeredAdapter(PuRecyclerViewActivity.this, new StaggeredAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(PuRecyclerViewActivity.this, "Click"+pos, Toast.LENGTH_SHORT).show();
            }
        }));
        rv_pu.addItemDecoration(new MyDecoration());
    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap = getResources().getDimensionPixelSize(R.dimen.dividerHeight2);
            outRect.set(gap, gap, gap, gap);
        }
    }
}
