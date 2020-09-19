package john.zhao.arunningman.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
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

import java.util.List;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.R;
import john.zhao.arunningman.adapter.SelectRecycleAdapter;
import john.zhao.arunningman.databinding.ActivitySelectBinding;
import john.zhao.arunningman.manager.BDManager;
import john.zhao.arunningman.model.SelectInfo;

public class SelectActivity extends BaseActivity {

    private ActivitySelectBinding binding;
    private BaiduMap baiduMap;
    private BDManager bdManager;
    private LatLng selectLatLng;

    private SelectRecycleAdapter adapter;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

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
    }

    @Override
    public void initView() {
        baiduMap = binding.mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        adapter = new SelectRecycleAdapter();
        binding.recSelect.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recSelect.setAdapter(adapter);
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
                }
                else
                {
                    binding.recSelect.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bdManager.setOnResultListener(new BDManager.OnResultListener() {
            @Override
            public void resut(List<SelectInfo> list) {
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
                    binding.tvAddressSelect.setText(reverseGeoCodeResult.getAddress());
                }
            }
        });

        adapter.setOnItemClickListener(new SelectRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnClick(SelectInfo selectInfo) {
                LatLng selectLL = new LatLng(selectInfo.getLatitude(), selectInfo.getLongtitude());
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(selectLL));
                baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(18f));
            }
        });
    }
}
