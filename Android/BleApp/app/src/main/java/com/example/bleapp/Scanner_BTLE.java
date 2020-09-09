package com.example.bleapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;


public class Scanner_BTLE
{

    private MainActivity ma;

    private BluetoothAdapter mBlutoothAdapter;
    private boolean mScanning;

    private long scanPeroid;
    private int signalStrength;
    private Handler mHandler;

    public Scanner_BTLE(MainActivity mainActivity, long scanPeroid, int signalStrength)
    {
        ma = mainActivity;

        mHandler = new Handler();

        this.scanPeroid = scanPeroid;
        this.signalStrength = signalStrength;

        final BluetoothManager bluetoothManager = (BluetoothManager)ma.getSystemService(Context.BLUETOOTH_SERVICE);

        mBlutoothAdapter = bluetoothManager.getAdapter();

    }

    public boolean isScanning()
    {
        return mScanning;
    }

    public void start()
    {
        if(!Utils.checkBluetooth(mBlutoothAdapter))
        {
            Log.d("BLEAPP", "stopScan");
            Utils.requestUserBluetooth(ma);
            ma.stopScan();
        }
        else
        {
            Log.d("BLEAPP", "scanLeDevice");
            scanLeDevice(true);
        }
    }

    public void stop()
    {
        scanLeDevice(false);
    }

    private void scanLeDevice(final boolean enable)
    {
        Log.d("BLEAPP", "scanLeDevice");
        if(enable && !mScanning) {
            Utils.toast(ma.getApplicationContext(), "Starting BLE Scan...");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("BLEAPP", "postDelayed");
                    Utils.toast(ma.getApplicationContext(), "Stopping BLE Scan...");

                    mScanning = false;
                    mBlutoothAdapter.stopLeScan(mLeScanCallback);
                    //mBlutoothAdapter.getBluetoothLeScanner().stopScan(mLeScanCallback);
                    ma.stopScan();
                }
            }, scanPeroid);


            Log.d("BLEAPP", "startLeScan");
            mScanning = true;
            mBlutoothAdapter.startLeScan(mLeScanCallback);
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes)
        {
            Log.d("BLEAPP", "LeScanCallback");
            final int new_rssi = i;
            if(i > signalStrength)
            {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("BLEAPP", "address:" + bluetoothDevice.getAddress());
                        ma.addDevice(bluetoothDevice, new_rssi);
                    }
                });
            }
        }
    };

}
