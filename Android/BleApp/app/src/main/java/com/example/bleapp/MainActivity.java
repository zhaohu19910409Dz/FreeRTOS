package com.example.bleapp;

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

    public static final UUID GAP_UUID = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    public static final UUID GATT_UUID = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_RX_TX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f6d00000");

    public static final UUID CHARACTERISTIC_TX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f6d00001");
    public static final UUID CHARACTERISTIC_RX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f6d0002");
    public static final UUID DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private final static String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;

    private HashMap<String, BTLE_Device> mBTDevicesHashMap;
    private ArrayList<BTLE_Device> mBTDevicesArrayList;
    private ListAdapter_BTLE_Devices adapter;

    private Button btn_Scan;

    private BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private Scanner_BTLE mBTLeScanner;

    BluetoothGatt mGatt = null;
    private BluetoothGattCallback mGattCallback;
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

        mGattCallback = new BluetoothGattCallback() {
            //当连接上设备或者失去连接时会回调该函数
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                Log.d("BLEAPP", "onConnectionStateChange");
                if(newState == BluetoothProfile.STATE_CONNECTED)
                {
                    if(mGatt != null)
                    {
                        mGatt.discoverServices();
                    }
                }
                else if(newState == BluetoothProfile.STATE_DISCONNECTED)
                {

                }
            }

            //当设备是否找到服务时，会回调该函数
            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
                Log.d("BLEAPP", "onServicesDiscovered");
                if(status == BluetoothGatt.GATT_SUCCESS)
                {
                    //在这里可以对服务进行解析，寻找到你需要的服务
                    BluetoothGattService gapServer = gatt.getService(GAP_UUID);
                    LogInfo(gatt, gapServer, "gap");

                    BluetoothGattService gattService = gatt.getService(GATT_UUID);
                    LogInfo(gatt, gattService, "gatt");

                    BluetoothGattService rtxServer = gatt.getService(BLE_RX_TX_UUID);
                    LogInfo(gatt, rtxServer, "rx tx");
//                    if(server != null)
//                    {
//                        Log.d("BLEAPP", "find Service by UUID");
//                        List<BluetoothGattCharacteristic> gattCharacteristics = server.getCharacteristics();
//                        for(BluetoothGattCharacteristic gattCharacteristic  : gattCharacteristics)
//                        {
//                            Log.d("BLEAPP", "UUID:"+gattCharacteristic.getUuid().toString());
//
//                            gatt.setCharacteristicNotification(gattCharacteristic, true);
//                            BluetoothGattDescriptor descriptor = gattCharacteristic.getDescriptor(DESCRIPTOR_UUID);
//                            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
//                            gatt.writeDescriptor(descriptor);
//                        }
//                    }
                }
                else
                {
                }
            }

            //当读取设备时会回调该函数
            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
                Log.d("BLEAPP", "onCharacteristicRead");
            }

            //设备发出通知时会调用到该接口
            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
                Log.d("BLEAPP", "onCharacteristicChanged");
            }

            //当向设备Descriptor中写数据时，会回调该函数
            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorWrite(gatt, descriptor, status);
                Log.d("BLEAPP", "onDescriptorWrite");
            }

            @Override
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                super.onReadRemoteRssi(gatt, rssi, status);
                Log.d("BLEAPP", "onReadRemoteRssi");
            }

            //当向Characteristic写数据时会回调该函数
            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
                Log.d("BLEAPP", "onCharacteristicWrite");
            }
        };
    }

    public void LogInfo(BluetoothGatt gatt, BluetoothGattService server, String name)
    {
        if(server != null)
        {
            Log.d("BLEAPP", name + ":find Service by UUID");
            List<BluetoothGattCharacteristic> gattCharacteristics = server.getCharacteristics();
            for(BluetoothGattCharacteristic gattCharacteristic  : gattCharacteristics)
            {
                Log.d("BLEAPP", "UUID:"+gattCharacteristic.getUuid().toString());

                List<BluetoothGattDescriptor> gattDescriptors = gattCharacteristic.getDescriptors();
                for(BluetoothGattDescriptor descriptor : gattDescriptors)
                {
                    Log.d("BLEAPP", "Descriptor UUID:" + descriptor.getUuid());
                }

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
        if(device != null)
        {
            Log.d("BLEAPP", "Find Device !!!!");
            mGatt = device.connectGatt(this, false, mGattCallback);

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