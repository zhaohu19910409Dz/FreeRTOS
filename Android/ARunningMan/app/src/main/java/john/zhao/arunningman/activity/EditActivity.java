package john.zhao.arunningman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.baidu.mapapi.model.LatLng;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.HashMap;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.LiveDataBus;
import john.zhao.arunningman.R;
import john.zhao.arunningman.databinding.ActivityEditBinding;
import john.zhao.arunningman.room.Address;

public class EditActivity extends BaseActivity {

    private ActivityEditBinding binding;
    private Intent intent;
    private LatLng latLng;
    private int TYPE = 1;

    private String sex ="女士";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initEvent();
    }

    @Override
    public void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        binding.setEditActivity(this);
        intent = new Intent();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        LiveDataBus.get().with("EditActivity").observerSticky(this, new EditActivityObserver(), true);
        binding.boxManEdit.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if(isChecked)
                {
                    sex = "男士";
                    binding.boxMissEdit.setChecked(false);
                }
            }
        });

        binding.boxMissEdit.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if(isChecked)
                {
                    sex = "女士";
                    binding.boxManEdit.setChecked(false);
                }
            }
        });
    }

    public void startActivity()
    {
        intent.setClass(this, SelectActivity.class);
        intent.putExtra("type", TYPE);
        startActivity(intent);
    }

    public void complete()
    {
        if(TextUtils.isEmpty(binding.etSelectEdit.getText()))
        {
            XToast.warning(this, "please select address").show();
            return;
        }
        else if(TextUtils.isEmpty(binding.etAddressEdit.getText()))
        {
            XToast.warning(this, "address is empty").show();
            return;
        }
        else if(TextUtils.isEmpty(binding.etNameEdit.getText()))
        {
            XToast.warning(this, "please input user name").show();
            return;
        }
        else if(TextUtils.isEmpty(binding.etPhoneEdit.getText()))
        {
            XToast.warning(this, "please input phone number").show();
            return;
        }
        else if(!binding.boxManEdit.isChecked() && !binding.boxMissEdit.isChecked())
        {
            XToast.warning(this, "please select sex").show();
            return;
        }

        Address address = new Address();

        address.setType(1);
        address.setAddress(binding.etAddressEdit.getText().toString());
        address.setName(binding.etNameEdit.getText().toString());
        address.setPhone(binding.etPhoneEdit.getText().toString());
        address.setSex(sex);
        if(latLng != null)
        {
            address.setLatitude(latLng.latitude);
            address.setLongitude(latLng.longitude);
        }

        LiveDataBus.get().with("TakeActivity").setStickyData(address);
        finish();
    }

    private class EditActivityObserver implements Observer<HashMap>{

        @Override
        public void onChanged(HashMap hashMap) {
            String address = (String)hashMap.get("address");
            LatLng latLng = (LatLng)hashMap.get("latLng");
            binding.etSelectEdit.setText(address);
        }
    }
}
