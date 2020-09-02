package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.john.ctronnel.utils.ToastUtil;

public class ToastActivity extends AppCompatActivity {

    private Button toast, toast1, toast2, toast3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        toast = (Button)findViewById(R.id.toast);
        toast1 = (Button)findViewById(R.id.toast1);
        toast2 = (Button)findViewById(R.id.toast2);
        toast3 = (Button)findViewById(R.id.toast3);

        OnClick onclick = new OnClick();
        toast.setOnClickListener(onclick);
        toast1.setOnClickListener(onclick);
        toast2.setOnClickListener(onclick);
        toast3.setOnClickListener(onclick);
    }

    class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.toast:
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.toast1:
                    Toast t = Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    break;
                case R.id.toast2:
                    Toast toastCustom = new Toast(getApplicationContext());
                    LayoutInflater inflater = LayoutInflater.from(ToastActivity.this);
                    View v = inflater.inflate(R.layout.layout_toast, null);
                    TextView text = (TextView)v.findViewById(R.id.tv_tv);
                    ImageView image = (ImageView)v.findViewById(R.id.iv_toast);
                    image.setImageResource(R.drawable.img2);
                    text.setText("My Toast View");
                    toastCustom.setView(v);
                    toastCustom.setDuration(Toast.LENGTH_LONG);
                    toastCustom.show();
                    break;
                case R.id.toast3:
                    ToastUtil.showMsg(getApplication(), "My Toast");
                    break;
            }
        }
    }
}