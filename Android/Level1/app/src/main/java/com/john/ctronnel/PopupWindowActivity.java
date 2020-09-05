package com.john.ctronnel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.john.ctronnel.utils.ToastUtil;

import org.w3c.dom.Text;

public class PopupWindowActivity extends AppCompatActivity {

    private Button btn;
    private PopupWindow pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        btn = (Button)findViewById(R.id.bt_pop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getLayoutInflater().inflate(R.layout.layout_pop, null);
                TextView text = (TextView)v.findViewById(R.id.tx_good);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pop.dismiss();
                        //do something
                        ToastUtil.showMsg(PopupWindowActivity.this, "Good");
                    }
                });
                pop = new PopupWindow(v, btn.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAsDropDown(btn);
            }
        });
    }
}