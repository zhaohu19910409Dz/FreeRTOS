package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.listView.ListActivity;
import com.john.ctronnel.gridview.GridViewActivity;
import com.john.ctronnel.recycleView.recycleViewActivity;

public class MainActivity extends AppCompatActivity {

    private Button ImageActivity;
    private Button ListActivity;
    private Button GridViewActivity;
    private Button RecyclerView;
    private Button WebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageActivity = (Button)findViewById(R.id.bt6);
        ListActivity = (Button)findViewById(R.id.listView);
        GridViewActivity = (Button)findViewById(R.id.gridView);
        RecyclerView = (Button)findViewById(R.id.recyclerView);
        WebView = (Button)findViewById(R.id.WebView);
        setListeners();
    }

    private void setListeners()
    {
        OnClick onClick = new OnClick();
        ImageActivity.setOnClickListener(onClick);
        ListActivity.setOnClickListener(onClick);
        GridViewActivity.setOnClickListener(onClick);
        RecyclerView.setOnClickListener(onClick);
        WebView.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        Intent intent = null;
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bt6:
                {
                    intent = new Intent(MainActivity.this, ImageActivity.class);
                    break;
                }
                case R.id.listView:
                {
                    intent = new Intent(MainActivity.this, ListActivity.class);
                    break;
                }
                case R.id.gridView:
                {
                    intent = new Intent(MainActivity.this, GridViewActivity.class);
                    break;
                }
                case R.id.recyclerView:
                {
                    intent = new Intent(MainActivity.this, recycleViewActivity.class);
                    break;
                }
                case R.id.WebView:
                {
                    intent = new Intent(MainActivity.this, WebViewActivity.class);
                    break;
                }
            }
            startActivity(intent);
        }
    }
}
