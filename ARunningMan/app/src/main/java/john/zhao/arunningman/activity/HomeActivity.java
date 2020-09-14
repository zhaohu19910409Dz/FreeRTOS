package john.zhao.arunningman.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.util.ArrayList;
import java.util.List;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.R;
import john.zhao.arunningman.adapter.HomeBannerAdapter;
import john.zhao.arunningman.bmob.MyUser;
import john.zhao.arunningman.manager.UserManager;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvNickName;
    private TextView tvMoney;
    private TextView tvID;
    private RadiusImageView ivPhoto;
    private BannerLayout bannerLayout;
    private TextView tvUp;

    private HomeBannerAdapter adapter;
    private List<Integer> list;
    private View bottomSheet;
    private BottomSheetBehavior<View> behavior;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        initView();
        initData();
    }

    @Override
    public void init() {
        list = new ArrayList<>();
    }

    @Override
    public void initView() {
        tvNickName = findViewById(R.id.tv_nickName_home);
        tvMoney = findViewById(R.id.tv_money_home);
        tvID = findViewById(R.id.tv_id_home);
        ivPhoto = findViewById(R.id.iv_photo_home);
        bannerLayout = findViewById(R.id.banner_home);
        tvUp = findViewById(R.id.tv_up_home);

        bottomSheet = findViewById(R.id.nes_home);
        behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void initData() {
        MyUser myUser = UserManager.get().getuser();
        String name = myUser.getName();
        double money = myUser.getMoney();

        tvNickName.setText(name);
        tvMoney.setText(String.valueOf(money));

        list.add(R.drawable.ima_51);
        list.add(R.drawable.ima_51);
        list.add(R.drawable.ima_51);
        adapter = new HomeBannerAdapter(list);
        bannerLayout.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_up_home:
            {
                if(behavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
                {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
            break;
        }
    }
}
