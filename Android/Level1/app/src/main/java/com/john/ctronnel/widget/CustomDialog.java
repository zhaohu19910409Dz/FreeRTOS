package com.john.ctronnel.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.john.ctronnel.R;

import org.w3c.dom.Text;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private TextView title,msg, cancle,ok;

    private IOnCancleListener cancleListener;
    private IOnOKListener okListener;

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public void setsMsg(String sMsg) {
        this.sMsg = sMsg;
    }

    public void setsCancle(String sCancle, IOnCancleListener cancleListener) {
        this.sCancle = sCancle;
        this.cancleListener = cancleListener;
    }

    public void setsOK(String sOK, IOnOKListener okListener) {
        this.sOK = sOK;
        this.okListener = okListener;
    }

    private String sTitle, sMsg, sCancle, sOK;
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("John","before set layout");
        setContentView(R.layout.layout_custom_dialog);
        Log.i("John","end set layout");

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x * 0.8);
        getWindow().setAttributes(p);

        title = (TextView)findViewById(R.id.title);
        msg = (TextView)findViewById(R.id.msg);
        cancle = (TextView)findViewById(R.id.cancle);
        ok = (TextView)findViewById(R.id.ok);

        cancle.setOnClickListener(this);
        ok.setOnClickListener(this);

        if(!TextUtils.isEmpty(sTitle))
        {
            title.setText(sTitle);
        }

        if(!TextUtils.isEmpty(sMsg))
        {
            msg.setText(sMsg);
        }

        if(!TextUtils.isEmpty(sCancle))
        {
            cancle.setText(sCancle);
        }

        if(!TextUtils.isEmpty(sOK))
        {
            ok.setText(sOK);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.cancle:
                if(cancleListener != null)
                {
                    cancleListener.OnCanle(this);
                    dismiss();
                }
                break;
            case R.id.ok:
                if(okListener != null)
                {
                    okListener.OnOK(this);
                    dismiss();
                }
                break;
        }
    }

    public interface IOnCancleListener{
        void OnCanle(CustomDialog dialog);
    }

    public interface IOnOKListener{
        void OnOK(CustomDialog dialog);
    }

}
