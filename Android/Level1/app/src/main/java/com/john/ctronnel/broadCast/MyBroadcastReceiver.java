package com.john.ctronnel.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AActivity", "onReveive");

        Bundle bundle = intent.getExtras();
        String title = bundle.getString("Title");
        Log.d("AActivity", "title:"+title);
    }
}
