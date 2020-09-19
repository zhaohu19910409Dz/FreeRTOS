package john.zhao.arunningman.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import john.zhao.arunningman.utils.Permission;

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
    private LinearLayout linkTake;
    private ImageView ivClose;

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

        tvUp.setOnClickListener(this);

        bottomSheet = findViewById(R.id.nes_home);
        behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        linkTake = bottomSheet.findViewById(R.id.lin_take_home);
        linkTake.setOnClickListener(this);

        ivClose = bottomSheet.findViewById(R.id.iv_close_home);
        ivClose.setOnClickListener(this);
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
        Permission permission = new Permission();
        permission.checkPermission(this);
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
            case R.id.iv_close_home:
            {
                if(behavior.getState() != BottomSheetBehavior.STATE_HIDDEN)
                {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
            break;
            case R.id.lin_take_home:
            {
                Intent intent = new Intent(HomeActivity.this, TakeActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.e("HomeActivity", "OK");
            }
        }
    }
}
