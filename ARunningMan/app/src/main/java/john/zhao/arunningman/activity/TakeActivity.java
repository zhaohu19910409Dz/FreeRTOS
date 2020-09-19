package john.zhao.arunningman.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.R;

public class TakeActivity extends BaseActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClien;
    private boolean isFirst = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take);
        initView();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        mMapView = findViewById(R.id.mapView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClien = new LocationClient(this);
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setCoorType("gcj02");
        locationClientOption.setScanSpan(0);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationDescribe(true);
        //locationClientOption.setLocationNotify(true);
        locationClientOption.setOpenGps(true);

        mLocationClien.setLocOption(locationClientOption);
        mLocationClien.registerLocationListener(new MyLocationListener());
        mLocationClien.start();
    }

    private class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation == null)
            {
                return;
            }
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(0)
                    .direction(100f)
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(myLocationData);

            if(isFirst)
            {
                LatLng latLng = new LatLng(bdLocation.getLongitude(), bdLocation.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                MapStatusUpdate update1 = MapStatusUpdateFactory.zoomBy(18f);
                mBaiduMap.setMapStatus(update);
                mBaiduMap.animateMapStatus(update1);
                isFirst = false;
            }
            mLocationClien.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mLocationClien.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mBaiduMap = null;
    }
}
