package com.john.ctronnel.utils;

import android.content.Context;
import android.widget.Toast;

import com.john.ctronnel.ToastActivity;

public class ToastUtil {
    public static Toast mToast;
    public static void showMsg(Context ctx, String msg)
    {
        if(mToast == null)
        {
            mToast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        }
        else
        {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
