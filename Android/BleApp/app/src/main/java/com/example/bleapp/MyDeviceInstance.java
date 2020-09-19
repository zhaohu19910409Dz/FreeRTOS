package com.example.bleapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import java.util.logging.Handler;

public class MyDeviceInstance {
    private BluetoothGatt gatt;
    private BluetoothGattService rtxGattServer;
    private BluetoothGattCharacteristic gattCharacteristic;
    private BluetoothDevice bluetoothDevice;

    private Handler handler;
    private static MyDeviceInstance myDeviceInstance = null;
    private MyDeviceInstance(){}

    public void SetHandler(Handler handler)
    {
        this.handler = handler;
    }

    public BluetoothGattService getRtxGattServer() {
        return rtxGattServer;
    }

    public void setRtxGattServer(BluetoothGattService rtxGattServer) {
        this.rtxGattServer = rtxGattServer;
    }

    public BluetoothGattCharacteristic getGattCharacteristic() {
        return gattCharacteristic;
    }

    public void setGattCharacteristic(BluetoothGattCharacteristic gattCharacteristic) {
        this.gattCharacteristic = gattCharacteristic;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothGatt getGatt() {
        return gatt;
    }

    public void setGatt(BluetoothGatt gatt) {
        this.gatt = gatt;
    }

    public static MyDeviceInstance getInstance()
    {
        if(myDeviceInstance == null)
        {
            myDeviceInstance = new MyDeviceInstance();
        }
        return myDeviceInstance;
    }
}
