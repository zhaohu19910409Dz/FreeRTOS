package john.zhao.arunningman;

import android.util.Log;

import java.util.List;
import java.util.Queue;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import john.zhao.arunningman.bmob.BBManager;
import john.zhao.arunningman.bmob.MyUser;
import john.zhao.arunningman.manager.UserManager;

public class Login {

    private LoginCallBack mLoginCallBack;
    private String phone;

    public  void checkPhoneCode(String phone, String code, LoginCallBack callBack)
    {
        Log.d("John", "checkPhoneCode");
        this.mLoginCallBack = callBack;
        this.phone = phone;
        BBManager.get().checkCode(phone, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null)
                {
                    Log.d("John", "check SMS Code OK");
                    findUserByPhone();
                }
                else
                {
                    Log.d("John", "check SMS Code failed");
                    mLoginCallBack.error(e.getMessage(), e.getErrorCode());
                }
            }
        });
    }

    private void findUserByPhone() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("phone", phone);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if(e == null)
                {
                    Log.d("John", "query.findObjects");
                    if(list.size() > 0)
                    {
                        MyUser user = list.get(0);
                        UserManager.get().saveUser(user);
                        mLoginCallBack.done(UserManager.get().getuser().getObjectId());
                    }
                    else
                    {
                        Log.d("John", "query not findObjects");
                        createUser();
                    }
                }
                else
                {
                    Log.d("John", "errorid:" + e.getErrorCode() + "errorinfo:"+e.getMessage());
                    mLoginCallBack.error(e.getMessage(), e.getErrorCode());
                }
            }
        });
    }

    private void createUser() {
        final MyUser user = new MyUser.Builder()
                .setName("John")
                .setPhone(phone)
                .setPhotoErl(null)
                .setPayPwd("0000")
                .setMoney(0.0)
                .setUpMoney(0.0)
                .build();

        user.save(new SaveListener<String>()
        {
            @Override
            public void done(String s, BmobException e)
            {
                Log.d("John", "save user done!!!");
                if(e == null)
                {

                    Log.d("John", "Save User OK");
                    UserManager.get().saveUser(user);
                    mLoginCallBack.done(UserManager.get().getuser().getObjectId());
                }
                else
                {
                    Log.d("John", "Save User Failed");
                    //mLoginCallBack.error(e.getMessage(), e.getErrorCode());
                }
            }
        });
    }

    public interface LoginCallBack{
        void done(String id);

        void error(String msg, int errCode);
    }
}
