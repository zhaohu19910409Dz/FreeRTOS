package john.zhao.arunningman.bmob;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class BBManager {

    private static BBManager mBBManger;
    private BBManager()
    {

    }

    public static BBManager get()
    {
        if(mBBManger == null)
        {
            synchronized (BBManager.class)
            {
                if(mBBManger == null)
                {
                    mBBManger = new BBManager();
                }
            }
        }
        return  mBBManger;
    }

    public void findUser(String id, QueryListener<MyUser> listener)
    {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.getObject(id, listener);

    }

    public void sendCode(String phone, QueryListener<Integer> listener)
    {
        BmobSMS.requestSMSCode(phone, "", listener);
    }


    public  void checkCode(String phone, String code, UpdateListener listener)
    {
        BmobSMS.verifySmsCode(phone, code, listener);
    }
}
