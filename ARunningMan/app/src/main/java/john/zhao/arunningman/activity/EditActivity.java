package john.zhao.arunningman.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.baidu.mapapi.model.LatLng;

import java.util.HashMap;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.LiveDataBus;
import john.zhao.arunningman.R;
import john.zhao.arunningman.databinding.ActivityEditBinding;
import john.zhao.arunningman.model.SelectInfo;

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
    }

    public void startActivity()
    {
        intent.setClass(this, SelectActivity.class);
        intent.putExtra("type", TYPE);
        startActivity(intent);
    }

    public void complete()
    {

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
