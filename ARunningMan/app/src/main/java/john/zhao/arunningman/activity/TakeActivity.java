package john.zhao.arunningman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.LiveDataBus;
import john.zhao.arunningman.R;
import john.zhao.arunningman.databinding.ActivityTakeBinding;
import john.zhao.arunningman.manager.BDMap;
import john.zhao.arunningman.room.Address;

public class TakeActivity extends BaseActivity {

    private ActivityTakeBinding binding;
    private Intent intent;
    private BDMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initEvent();
    }

    @Override
    public void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_take);
        binding.include.setTakeActivity(this);

        intent = new Intent();
    }

    @Override
    public void initView()
    {
        map = new BDMap(this);
        map.location(0, binding.mapView.getMap());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        LiveDataBus.get().with("TakeActivity").observerSticky(this, new TakeActivityObserver(), true);
    }

    public void startActivity(int  type)
    {
        Log.i("John", "TakeActivity::startActivity type:" + type);
        if(type == 1)
        {
            intent.setClass(this, EditActivity.class);
            startActivity(intent);
        }
        else if(type == 2)
        {
            intent.setClass(this, AddressActivity.class);
            startActivity(intent);
        }

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        binding.mapView.onDestroy();
        map.destroy();
    }

    public void showBottomDialog()
    {

    }

    public void commit()
    {

    }

    private class TakeActivityObserver implements Observer<Address>
    {
        @Override
        public void onChanged(Address address)
        {
            if(address.getType() == 1)
            {
                binding.include.setTakeAddress(address);
            }
            else
            {
                binding.include.setCollectAddress(address);
            }
        }
    }
}
