package com.example.bleapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.UUID;

public class MyGattCallBack extends BluetoothGattCallback {

    public static final UUID GAP_UUID = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    public static final UUID GATT_UUID = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_RX_TX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f6d0000");

    public static final UUID CHARACTERISTIC_TX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f6d0001");
    public static final UUID CHARACTERISTIC_RX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f6d0002");

    private BluetoothGatt mGatt;
    private BluetoothGattService rtxGattServer;
    private BluetoothGattCharacteristic gattCharacteristic;

    private Handler handler;
    public MyGattCallBack(Handler handler)
    {
        this.handler = handler;
    }
    //当连接上设备或者失去连接时会回调该函数
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        Log.d("BLEAPP", "onConnectionStateChange");
        if(newState == BluetoothProfile.STATE_CONNECTED)
        {
            if(gatt != null)
            {
                gatt.discoverServices();
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
//                    List<BluetoothGattService> services = gatt.getServices();
//                    for(BluetoothGattService server: services)
//                    {
//                        Log.d("BLEAPP", "service UUID:"+server.getUuid().toString());
//                    }

            //在这里可以对服务进行解析，寻找到你需要的服务
            Log.d("BLEAPP", "GAP UUID");
            BluetoothGattService gapServer = gatt.getService(GAP_UUID);

            Log.d("BLEAPP", "GATT UUID");
            BluetoothGattService gattService = gatt.getService(GATT_UUID);

            Log.d("BLEAPP", "BLE_RX_TX_UUID UUID");
            mGatt = gatt;
            rtxGattServer = gatt.getService(BLE_RX_TX_UUID);
            gattCharacteristic = rtxGattServer.getCharacteristic(CHARACTERISTIC_RX_UUID);
            if(gattCharacteristic != null)
            {
                Log.d("BLEAPP", "BLE_RX_TX_UUID UUID");
                MyDeviceInstance.getInstance().setGatt(mGatt);
                MyDeviceInstance.getInstance().setRtxGattServer(rtxGattServer);
                MyDeviceInstance.getInstance().setGattCharacteristic(gattCharacteristic);
                handler.sendEmptyMessageDelayed(1001, 1000);
                //handler.sendEmptyMessageDelayed(1000, 1000);
            }
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
        Log.d("BLEAPP", "onCharacteristicWrite" + characteristic.getValue().toString());

    }
}
