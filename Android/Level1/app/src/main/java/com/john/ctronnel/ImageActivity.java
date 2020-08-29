package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    private ImageView netImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        netImg = (ImageView)findViewById(R.id.netPic);
        //Glide.with(this).load("http://goo.gl/gEgYUd").into(netImg);
    }
}
