package john.zhao.arunningman.manager;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class BDMap {
    private Context context;
    private LocationClient mLocationClient;
    private BaiduMap baiduMap;

    public BDMap(Context context)
    {
        this.context = context;
    }

    public void location(int time, BaiduMap map)
    {
        this.baiduMap = map;
        baiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setScanSpan(time);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //LatLng latLng = new LatLng(38.9433671045255,117.36299499999993);
                //baiduMap.setMyLocationData(new MyLocationData.Builder().latitude(latLng.latitude).longitude(latLng.longitude).build());
                baiduMap.setMyLocationData(new MyLocationData.Builder().latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build());
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
                baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(18f));
                stop();
            }
        });
    }

    public void start()
    {
        mLocationClient.start();
    }

    public void stop()
    {
        mLocationClient.stop();
    }

    public void destroy()
    {
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        baiduMap = null;
    }

    public String dist(double dist)
    {
        String str = String.valueOf(dist);
        int of = str.indexOf(".");
        if(of != -1)
        {
            String substring = str.substring(0, of + 2);
            return substring;
        }
        return str;
    }
}
