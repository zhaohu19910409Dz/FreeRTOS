package com.john.ctronnel.recycleView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.john.ctronnel.R;

public class LinearRecycleViewActivity extends AppCompatActivity {

    private RecyclerView mRvmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_recycle_view);

        mRvmain = (RecyclerView) findViewById(R.id.rv_main);
        mRvmain.setLayoutManager(new LinearLayoutManager(LinearRecycleViewActivity.this));
        mRvmain.addItemDecoration(new MyItemDecoration());
        mRvmain.setAdapter(new LinearAdapter(LinearRecycleViewActivity.this, new LinearAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(LinearRecycleViewActivity.this, "click"+pos, Toast.LENGTH_LONG).show();
            }
        }));

    }

    class MyItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }
}
