package com.example.bleapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;


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
            Utils.requestUserBluetooth(ma);
            ma.stopScan();
        }
        else
        {
            scanLeDevice(true);
        }
    }

    public void stop()
    {
        scanLeDevice(false);
    }

    private void scanLeDevice(final boolean enable)
    {
        if(enable && !mScanning) {
            Utils.toast(ma.getApplicationContext(), "Starting BLE Scan...");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.toast(ma.getApplicationContext(), "Stopping BLE Scan...");

                    mScanning = false;
                    mBlutoothAdapter.stopLeScan(mLeScanCallback);
                    ma.stopScan();
                }
            }, scanPeroid);

            mScanning = true;
            mBlutoothAdapter.startLeScan(mLeScanCallback);
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes)
        {
            final int new_rssi = i;
            if(i > signalStrength)
            {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ma.addDevice(bluetoothDevice, i);
                    }
                });
            }
        }
    };

}
