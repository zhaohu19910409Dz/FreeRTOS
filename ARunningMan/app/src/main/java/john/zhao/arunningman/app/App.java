package john.zhao.arunningman.app;

import android.Manifest;
import android.app.Application;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.xuexiang.xui.XUI;

import cn.bmob.v3.Bmob;

public class App extends Application {

    private String APP_KEY = "29aebf805084336743316d96de80c307";
    @Override
    public void onCreate() {
        Log.d("John", "App onCreate");
        super.onCreate();
        //bmob iniy
        Bmob.initialize(this, APP_KEY);
        //xui init
        XUI.init(this);
        XUI.debug(true);

        //init baidu map
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }
}
