package com.john.ctronnel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.john.ctronnel.utils.ToastUtil;

public class ProgressActivity extends AppCompatActivity {

    private ProgressBar pb3;
    private Button start;
    private Button progressDialog1,progressDialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        pb3 = (ProgressBar)findViewById(R.id.pb3);
        pb3.setProgress(30);

        progressDialog1 = (Button)findViewById(R.id.progressDialog1);
        progressDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(ProgressActivity.this);
                dialog.setTitle("Tips");
                dialog.setMessage("Loading...");
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        ToastUtil.showMsg(ProgressActivity.this, "Cancle.....");
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        progressDialog2 = (Button)findViewById(R.id.progressDialog2);
        progressDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(ProgressActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setTitle("Tips");
                dialog.setMessage("Downing ....");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        
                    }
                });
                dialog.setProgress(60);
                dialog.setSecondaryProgress(80);
                dialog.show();
            }
        });

        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(pb3.getProgress() < 100)
            {
                handler.postDelayed(runnable, 500);
            }
            else
            {
                ToastUtil.showMsg(ProgressActivity.this, "Load Finished!!!");
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            pb3.setProgress(pb3.getProgress()+5);
            handler.sendEmptyMessage(0);
        }
    };
}