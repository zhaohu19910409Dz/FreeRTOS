package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.recycleView.recycleViewActivity;

public class UIActivity extends AppCompatActivity {

    private Button ImageActivity;
    private Button ListActivity;
    private Button GridViewActivity;
    private Button RecyclerView;
    private Button WebView;
    private Button ToastView;
    private Button AlertDialogBtn;
    private Button ProgressActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i);

        ImageActivity = (Button)findViewById(R.id.bt6);
        ListActivity = (Button)findViewById(R.id.listView);
        GridViewActivity = (Button)findViewById(R.id.gridView);
        RecyclerView = (Button)findViewById(R.id.recyclerView);
        WebView = (Button)findViewById(R.id.WebView);
        ToastView = (Button)findViewById(R.id.ToastView);
        AlertDialogBtn = (Button)findViewById(R.id.alertDialog);
        ProgressActivity = (Button)findViewById(R.id.progessActivity);
        setListeners();
    }

    private void setListeners()
    {
        UIActivity.OnClick onClick = new UIActivity.OnClick();
        ImageActivity.setOnClickListener(onClick);
        ListActivity.setOnClickListener(onClick);
        GridViewActivity.setOnClickListener(onClick);
        RecyclerView.setOnClickListener(onClick);
        WebView.setOnClickListener(onClick);
        ToastView.setOnClickListener(onClick);
        AlertDialogBtn.setOnClickListener(onClick);
        ProgressActivity.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        Intent intent = null;
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bt6:
                {
                    intent = new Intent(UIActivity.this, ImageActivity.class);
                    break;
                }
                case R.id.listView:
                {
                    intent = new Intent(UIActivity.this, com.john.ctronnel.listView.ListActivity.class);
                    break;
                }
                case R.id.gridView:
                {
                    intent = new Intent(UIActivity.this, com.john.ctronnel.gridview.GridViewActivity.class);
                    break;
                }
                case R.id.recyclerView:
                {
                    intent = new Intent(UIActivity.this, recycleViewActivity.class);
                    break;
                }
                case R.id.WebView:
                {
                    intent = new Intent(UIActivity.this, WebViewActivity.class);
                    break;
                }
                case R.id.ToastView:
                {
                    intent = new Intent(UIActivity.this, ToastActivity.class);
                    break;
                }
                case R.id.alertDialog:
                {
                    intent = new Intent(UIActivity.this, AlertDialogActivity.class);
                    break;
                }
                case R.id.progessActivity:
                {
                    intent = new Intent(UIActivity.this, ProgressActivity.class);
                    break;
                }
            }
            startActivity(intent);
        }
    }
}