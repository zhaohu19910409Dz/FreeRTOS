package john.zhao.arunningman.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuexiang.xui.widget.toast.XToast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.R;
import john.zhao.arunningman.bmob.BBManager;
import john.zhao.arunningman.bmob.MyUser;
import john.zhao.arunningman.manager.UserManager;

public class StatActivity extends BaseActivity {

    private Handler handler;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if(msg.what == 1000)
                {
                    //Next
                    intent.setClass(StatActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    intent.setClass(StatActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        if(!TextUtils.isEmpty(id))
        {
            findUser(id);
            handler.sendEmptyMessageDelayed(1000, 3000);
        }
        else
        {
            handler.sendEmptyMessageDelayed(1001, 3000);
        }
    }

    private void findUser(String id) {
        BBManager.get().findUser(id, new QueryListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if(e == null)
                {
                    Log.d("John", "Find User by id");
                    UserManager.get().saveUser(user);
                    handler.sendEmptyMessageDelayed(1000, 2000);
                }
                else
                {
                    Log.d("John", "Not Find User by id");
                    XToast.warning(StatActivity.this, "Error:"+e.getMessage()).show();
                }
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
