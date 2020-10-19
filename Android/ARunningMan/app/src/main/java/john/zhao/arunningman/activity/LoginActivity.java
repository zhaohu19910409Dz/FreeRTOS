package john.zhao.arunningman.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.xuexiang.xui.widget.toast.XToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.R;
import john.zhao.arunningman.bmob.BBManager;
import john.zhao.arunningman.bmob.MyUser;
import john.zhao.arunningman.manager.UserManager;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivClose;
    private ImageView ivSend;
    private EditText etPhone;

    private String sPhone;
    Intent intent;

    private String[] permisson =
    {
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1000)
            {
                Log.d("John", "find user infomation by phone num!!!");
                intent.setClass(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            else if(msg.what == 1001)
            {
                registerUserByPhone();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init permission
        ActivityCompat.requestPermissions(this, permisson, 1);

        init();
        initView();
    }

    @Override
    public void init() {
        intent = new Intent();
    }

    @Override
    public void initView() {
        ivClose = (ImageView)findViewById(R.id.iv_close_login);
        ivSend = (ImageView)findViewById(R.id.iv_send_login);
        etPhone = (EditText)findViewById(R.id.et_phone_login);

        ivClose.setOnClickListener(this);
        ivSend.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_close_login:
            {
                Log.d("John", "Close Login");
                finish();
            }
            break;
            case R.id.iv_send_login:
            {
                Log.d("John", "Click Send");
                sendCode();
            }
            break;
        }
    }

    private void sendCode()
    {
        sPhone = etPhone.getText().toString();

        if(sPhone.length() != 11)
        {
            XToast.warning(this, "Error phone num").show();
            return;
        }

        Log.d("John", "Start Login by phone num:" + sPhone);
        findUserByPhone(sPhone);
    }
    private void registerUserByPhone()
    {
        Log.d("John", "Not find user infomation by phone num!!!");
        /*need regiter*/
        BBManager.get().sendCode(sPhone, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e == null)
                {
                    Log.d("John", "Jump to CheckActivity");
                    intent.setClass(LoginActivity.this, CheckActivity.class);
                    intent.putExtra("phone", sPhone);
                    startActivityForResult(intent, 1000 );
                }
                else
                {
                    Log.d("John", "Send Codec Faild!!");
                    XToast.warning(LoginActivity.this, "Failed"+e.getErrorCode()).show();
                }
            }
        });
    }

    private void findUserByPhone(String sPhone)
    {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("phone", sPhone);
        query.findObjects(new FindListener<MyUser>()
        {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if(e == null)
                {
                    Log.d("John", "query.findObject size=" + list.size());
                    if(list.size() > 0)
                    {
                        MyUser user = list.get(0);
                        UserManager.get().saveUser(user);
                        handler.sendEmptyMessageDelayed(1000, 1000);
                    }
                    else
                    {
                        Log.d("John", "query not findObjects");
                        handler.sendEmptyMessageDelayed(1001, 1000);
                    }
                }
                else
                {
                    XToast.warning(LoginActivity.this, "请检查设备的网络设置！！！").show();
                    Log.d("John", "errorid:" + e.getErrorCode() + "errorinfo:"+e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("John", "Back Login Activity");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && requestCode ==RESULT_OK)
        {
            Log.d("John", "Jump to HomeActivity");
            intent.setClass(LoginActivity.this, StatActivity.class);
            startActivity(intent);
            finish();
            Log.d("John","onActivity:" +UserManager.get().getuser().getObjectId());
        }
    }
}
