package john.zhao.arunningman.bmob;

import cn.bmob.v3.BmobObject;

public class MyUser extends BmobObject {

    private String name;
    private String phone;
    private String photoErl;
    private String payPwd;

    private Double money;
    private Double upMoney;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoErl() {
        return photoErl;
    }

    public void setPhotoErl(String photoErl) {
        this.photoErl = photoErl;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getUpMoney() {
        return upMoney;
    }

    public void setUpMoney(Double upMoney) {
        this.upMoney = upMoney;
    }

    public static class Builder{
        private String name;
        private String phone;
        private String photoErl;
        private String payPwd;

        private Double money;
        private Double upMoney;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setPhotoErl(String photoErl) {
            this.photoErl = photoErl;
            return this;
        }

        public Builder setPayPwd(String payPwd) {
            this.payPwd = payPwd;
            return this;
        }

        public Builder setMoney(Double money) {
            this.money = money;
            return this;
        }

        public Builder setUpMoney(Double upMoney) {
            this.upMoney = upMoney;
            return this;
        }

        public MyUser build()
        {
            MyUser myUser = new MyUser();
            myUser.setName(name);
            myUser.setPhone(phone);
            myUser.setPhotoErl(photoErl);
            myUser.setPayPwd(payPwd);
            myUser.setMoney(money);
            myUser.setUpMoney(upMoney);
            return myUser;
        }
    }
}
