package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.john.ctronnel.widget.CustomDialog;

import java.util.logging.Logger;

public class CustomDialogActivity extends AppCompatActivity {

    private Button dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        dialog1 = (Button)findViewById(R.id.btn_custom_dialog);
        dialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("John","Before OnClick");
                CustomDialog customDialog = new CustomDialog(CustomDialogActivity.this);
                Log.i("John","End OnClick");
                customDialog.setsTitle("Tips");
                customDialog.setsMsg("Are you sure????");
                customDialog.setsCancle("Cancle", new CustomDialog.IOnCancleListener() {
                    @Override
                    public void OnCanle(CustomDialog dialog) {

                    }
                });

                customDialog.setsOK("OK", new CustomDialog.IOnOKListener() {
                    @Override
                    public void OnOK(CustomDialog dialog) {

                    }
                });

                customDialog.show();
            }
        });
    }
}