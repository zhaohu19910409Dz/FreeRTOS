package john.zhao.arunningman.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xuexiang.xui.widget.edittext.verify.VerifyCodeEditText;
import com.xuexiang.xui.widget.toast.XToast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.Login;
import john.zhao.arunningman.R;
import john.zhao.arunningman.bmob.BBManager;
import john.zhao.arunningman.manager.UserManager;

public class CheckActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivClose;
    private TextView tvPhone;
    private ImageView ivUp;
    private VerifyCodeEditText etCode;
    private MaterialButton btSend;

    private String phone;

    private int count = 60;
    private MiniLoadingDialog miniLoadingDialog;

    private boolean isSend = true;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(isSend)
            {
                if(msg.what == 1)
                {
                    btSend.setEnabled(true);
                    btSend.setText("重新发送");
                    btSend.setBackgroundColor(ActivityCompat.getColor(CheckActivity.this, R.color.xui_btn_blue_normal_color));
                    isSend = false;
                    count = 60;
                }
                else
                {
                    count--;
                    btSend.setText(count + "s");
                    handler.sendEmptyMessageDelayed(count, 1000);
                }
            }


            if(msg.what == 1001)
            {
                miniLoadingDialog.dismiss();
                //Intent intent = new Intent(CheckActivity.this, HomeActivity.class);
                setResult(RESULT_OK);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        init();
        initView();
        initData();
        initEvent();
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void init() {
        miniLoadingDialog = WidgetUtils.getMiniLoadingDialog(this);
        miniLoadingDialog.setDialogSize(200, 200);
    }

    @Override
    public void initView() {
        ivClose = findViewById(R.id.iv_close_check);
        etCode = findViewById(R.id.et_codec_check);
        btSend = findViewById(R.id.btn_send_check);

        ivClose.setOnClickListener(this);
        etCode.setOnClickListener(this);
        btSend.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        Log.d("John", "phone:"+phone);
        //tvPhone.setText(phone);
    }

    @Override
    public void initEvent() {
        etCode.setOnInputListener(new VerifyCodeEditText.OnInputListener() {
            @Override
            public void onComplete(String input) {
                Log.d("John", "Edit onComplete");
                checkPhoneCode(input);
            }

            @Override
            public void onChange(String input) {

            }

            @Override
            public void onClear() {

            }
        });
    }

    private void checkPhoneCode(String code) {
        miniLoadingDialog.show();
        Login login = new Login();
        login.checkPhoneCode(phone, code, new Login.LoginCallBack() {
            @Override
            public void done(String id) {
                Log.d("John", "put User ID");
                putUserId(id);
            }

            @Override
            public void error(String msg, int errCode) {
                Log.d("John", "CheckoutPhoneCode Failed");
                miniLoadingDialog.dismiss();
                XToast.warning(CheckActivity.this, msg + errCode);
                Log.e("CheckActivity", "error info:" + msg + "errCode:"+errCode);
            }
        });
    }

    private void putUserId(String id) {
        Log.d("John", "putUserID");
        SharedPreferences sharedPreferences = getSharedPreferences("user" , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.commit();
        handler.sendEmptyMessageDelayed(1001, 500);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_close_check:
                finish();
                break;
            case R.id.btn_send_check:
                sendCode();
                break;
        }
    }

    private void sendCode()
    {
        isSend = true;
        BBManager.get().sendCode(phone, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {

            }
        });
    }
}