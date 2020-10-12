package john.zhao.arunningman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.HashMap;
import java.util.List;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.LiveDataBus;
import john.zhao.arunningman.R;
import john.zhao.arunningman.adapter.SelectRecycleAdapter;
import john.zhao.arunningman.databinding.ActivitySelectBinding;
import john.zhao.arunningman.manager.BDManager;
import john.zhao.arunningman.model.SelectInfo;
import john.zhao.arunningman.room.Address;
import john.zhao.arunningman.room.AddressManager;

public class SelectActivity extends BaseActivity {

    private ActivitySelectBinding binding;
    private BaiduMap baiduMap;
    private BDManager bdManager;
    private LatLng selectLatLng;

    private SelectRecycleAdapter adapter;
    private InputMethodManager inputMethodManager;

    private View bottomSheet;
    private String address;
    private int type;
    private BottomSheetBehavior<View> behavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_select);

        init();
        initView();
        initData();
        initEvent();
    }

    @Override
    public void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select);
        binding.setSelectActivity(this);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
    }

    public void gone(){
        binding.recSelect.setVisibility(View.GONE);
        binding.ivCloseSelect.setVisibility(View.GONE);
        inputMethodManager.hideSoftInputFromWindow(binding.etContentSelect.getWindowToken(), 0);
    }


    public void complete()
    {
        if(type == 1) {
            HashMap hashMap = new HashMap();
            hashMap.put("address", address);
            hashMap.put("latLng", selectLatLng);
            LiveDataBus.get().with("EditActivity").setStickyData(hashMap);
            finish();
        }
        else if(type == 2)
        {
            if(behavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
            {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                binding.include.tvAddressBottomSelect.setText(address);
                binding.include.boxMissBottomSelect.setChecked(true);
            }
        }
    }

    public void hideBehavior()
    {
        if(behavior.getState() != BottomSheetBehavior.STATE_HIDDEN)
        {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    public void addAddress()
    {
        if(TextUtils.isEmpty(binding.include.etAddressBottomSelect.getText().toString()))
        {
            XToast.warning(this, "address empty").show();
            return;
        }
        else if(TextUtils.isEmpty(binding.include.etNameBottomSelect.getText().toString()))
        {
            XToast.warning(this, "name empty").show();
            return;
        }
        else if(TextUtils.isEmpty(binding.include.etPhoneBottomSelect.getText().toString()))
        {
            XToast.warning(this, "phone empty").show();
            return;
        }
        else if(!binding.include.boxManBottomSelect.isChecked() &&
                 !binding.include.boxMissBottomSelect.isChecked())
        {
            XToast.warning(this, "sex empty").show();
            return;
        }

        Address address = new Address();
        address.setAddress(binding.include.etAddressBottomSelect.getText().toString());
        address.setType(2);
        address.setName(binding.include.etNameBottomSelect.getText().toString());
        address.setPhone(binding.include.etPhoneBottomSelect.getText().toString());
        address.setSex(binding.include.boxManBottomSelect.isChecked() ? "先生" : "女士");
        address.setLatitude(selectLatLng.latitude);
        address.setLongitude(selectLatLng.longitude);

        AddressManager addressManager = new AddressManager(this);
        addressManager.addAddress(address);

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void initView() {
        baiduMap = binding.mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        adapter = new SelectRecycleAdapter();
        binding.recSelect.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recSelect.setAdapter(adapter);

        bottomSheet = binding.nesSelect;
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void initData() {
//        LatLng latLng = new LatLng();
//        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
//        MapStatusUpdate update1 = MapStatusUpdateFactory.zoomTo(18f);
//        baiduMap.setMapStatus(update);
//        baiduMap.animateMapStatus(update1);
    }

    @Override
    public void initEvent() {
        bdManager = new BDManager();
        binding.etContentSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0)
                {
                     bdManager.startPoi(charSequence.toString());
                     binding.recSelect.setVisibility(View.VISIBLE);
                     binding.ivCloseSelect.setVisibility(View.VISIBLE);
                }
                else
                {
                    gone();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bdManager.setOnResultListener(new BDManager.OnResultListener() {
            @Override
            public void resut(List<SelectInfo> list) {
                binding.recSelect.removeAllViews();
                adapter.setList(list);

            }
        });

        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if(mapStatus != null)
                {
                    selectLatLng = mapStatus.target;
                    bdManager.setLatLng(selectLatLng);
                    binding.tvLatitudeSelect.setText(String.valueOf(selectLatLng.latitude));
                    binding.tvLongitudeSelect.setText(String.valueOf(selectLatLng.longitude));
                    inputMethodManager.hideSoftInputFromWindow(binding.etContentSelect.getWindowToken(), 0);
                    binding.recSelect.setVisibility(View.GONE);
                }
            }
        });

        bdManager.setOnGetGeoCoderResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if(reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
                {
                    Log.e("SelectActivity", "NO Address");
                }
                else
                {
                    address = reverseGeoCodeResult.getAddress();
                    binding.tvAddressSelect.setText(address);
                }
            }
        });

        adapter.setOnItemClickListener(new SelectRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnClick(SelectInfo selectInfo) {
                LatLng selectLL = new LatLng(selectInfo.getLatitude(), selectInfo.getLongtitude());
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(selectLL));
                baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(18f));
                gone();
            }
        });
    }
}
