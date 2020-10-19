package john.zhao.arunningman.manager;

import john.zhao.arunningman.bmob.MyUser;

public class UserManager {

    private MyUser mMyUser;
    private static UserManager mUserManager;

    private UserManager()
    {
    }

    public static UserManager get()
    {
        if(mUserManager == null)
        {
            mUserManager = new UserManager();
        }
        return mUserManager;
    }
    public  void saveUser(MyUser user)
    {
        this.mMyUser = user;
    }

    public MyUser getuser()
    {
        return mMyUser;
    }

    public void removeUser()
    {
        mMyUser = null;
    }
}
