package com.john.ctronnel.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.john.ctronnel.R;

public class ContinerActivity extends AppCompatActivity implements AFragment.IOnMessageClick{

    private Button change;
    AFragment aFragment = null;
    BFragment bFragment = null;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continer);

        //aFragment = new AFragment();
        aFragment = AFragment.newInstance("Params");
        getFragmentManager().beginTransaction().add(R.id.fg_continer, aFragment, "TagA").commitAllowingStateLoss();

        change = (Button)findViewById(R.id.change);
//        change.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                if(bFragment == null)
//                {
//                    bFragment = new BFragment();
//                }
//                getFragmentManager().beginTransaction().replace(R.id.fg_continer, bFragment).commitAllowingStateLoss();
//            }
//        });

        tv_title = (TextView)findViewById(R.id.tv_title);
    }

    public void setData(String title)
    {
        tv_title.setText(title);
    }

    @Override
    public void onClick(String text) {
        tv_title.setText(text);
    }
}