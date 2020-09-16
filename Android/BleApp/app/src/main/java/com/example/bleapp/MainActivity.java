package com.example.bleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;

    private HashMap<String, BTLE_Device> mBTDevicesHashMap;
    private ArrayList<BTLE_Device> mBTDevicesArrayList;
    private ListAdapter_BTLE_Devices adapter;

    private Button btn_Scan;

    private BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private Scanner_BTLE mBTLeScanner;

    BluetoothGatt mGatt = null;
    private MyGattCallBack mGattCallback;

    BluetoothGattService rtxServer = null;
    BluetoothGattCharacteristic gattCharacteristic = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Utils.toast(getApplicationContext(), "BLE not support");
            finish();
        }

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        }, 1);

        mBTStateUpdateReceiver = new BroadcastReceiver_BTState(getApplicationContext());
        mBTLeScanner = new Scanner_BTLE(this, 7500, -75);

        mBTDevicesHashMap = new HashMap<>();
        mBTDevicesArrayList = new ArrayList<>();

        adapter = new ListAdapter_BTLE_Devices(this, R.layout.btle_device_list_item, mBTDevicesArrayList);

        MyListView listView = new MyListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        ((ScrollView)findViewById(R.id.scro_view)).addView(listView);

        btn_Scan = (Button)findViewById(R.id.btn_scan);
        btn_Scan.setOnClickListener(this);

        //mGattCallback = new MyGattCallBack();
    }

    public void LogInfo(BluetoothGatt gatt, BluetoothGattService server, String name)
    {
        if(server != null)
        {
            List<BluetoothGattCharacteristic> gattCharacteristics = server.getCharacteristics();
            for(BluetoothGattCharacteristic gattCharacteristic  : gattCharacteristics)
            {
                Log.d("BLEAPP", "UUID:"+gattCharacteristic.getUuid().toString());
                List<BluetoothGattDescriptor> gattDescriptors = gattCharacteristic.getDescriptors();

            }
        }
    }

    @Override
    protected void onStart() {
        Log.d("BLEAPP", "onStart");
        super.onStart();
        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onResume() {
        Log.d("BLEAPP", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("BLEAPP", "onPause");
        super.onPause();
        startScan();
    }

    @Override
    protected void onStop() {
        Log.d("BLEAPP", "onStop");
        super.onStop();

        unregisterReceiver(mBTStateUpdateReceiver);
        stopScan();
    }

    @Override
    protected void onDestroy() {
        Log.d("BLEAPP", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("BLEAPP", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT)
        {
            if(resultCode == RESULT_OK)
            {

            }
            else if(resultCode == RESULT_CANCELED)
            {
                Utils.toast(getApplicationContext(), "Please turn on Bluetooth");
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_scan:
                Utils.toast(getApplicationContext(), "Scan Button Pressed");
                if(!mBTLeScanner.isScanning())
                {
                    Log.d("BLEAPP", "startScan");
                    startScan();
                }
                else
                {
                    Log.d("BLEAPP", "stopScan");
                    stopScan();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
        TextView tv_address = (TextView)view.findViewById(R.id.tv_address);

        String name  = tv_name.getText().toString();
        String address = tv_address.getText().toString();
        Log.d("BLEAPP", "Name:" +name + "Address:" + address);

        BluetoothDevice device = getDevice(address);
        MyDeviceInstance.getInstance().setBluetoothDevice(device);
        if(device != null)
        {
            Log.d("BLEAPP", "Find Device !!!!");

            //mGatt = device.connectGatt(this, false, mGattCallback);
            Intent intent = new Intent(MainActivity.this, HandleDataActivity.class);
            startActivity(intent);
        }
    }


    public void startScan()
    {
        btn_Scan.setText("Scanning...");
        mBTDevicesArrayList.clear();
        mBTDevicesHashMap.clear();

        adapter.notifyDataSetChanged();
        mBTLeScanner.start();
    }

    public void stopScan()
    {
        btn_Scan.setText("Scan Again");
        mBTLeScanner.stop();

    }

    public void addDevice(BluetoothDevice bluetoothDevice, int i)
    {
        String address = bluetoothDevice.getAddress();
        if(!mBTDevicesHashMap.containsKey(address))
        {
            BTLE_Device btle_device = new BTLE_Device(bluetoothDevice);
            btle_device.setRSSI(i);

            mBTDevicesHashMap.put(address, btle_device);
            mBTDevicesArrayList.add(btle_device);
        }
        else
        {
            mBTDevicesHashMap.get(address).setRSSI(i);
        }

        adapter.notifyDataSetChanged();
    }

    public BluetoothDevice getDevice(String address)
    {
        BluetoothDevice device = null;
        if(mBTDevicesHashMap.containsKey(address))
        {
            BTLE_Device leDevice = mBTDevicesHashMap.get(address);
            device = leDevice.getDevice();
        }
        return device;
    }
}