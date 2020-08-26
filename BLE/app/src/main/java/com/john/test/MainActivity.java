package com.john.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bluetoothAdapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // bluetoothManager BLE需要这个类
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        }

        checkBLESupport();
        onEnableBlueTooth();
        onDiscoverable();

        //BluetoothGattService service = new BluetoothGattService(BluetoothGattService.SERVICE_TYPE_PRIMARY);
        //BluetoothGattService service = new BluetoothGattService(YOUR_SERVICE_UUID, BluetoothGattService.SERVICE_TYPE_PRIMARY);

    }

    //检查设备是否支持BLE
    public void checkBLESupport() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持BLE", Toast.LENGTH_LONG).show();
            Log.d("kaikai", "不支持BLE");

        } else {
            Toast.makeText(this, "支持BLE", Toast.LENGTH_LONG).show();
            Log.d("kaikai", "支持BLE");
        }
    }

    //开启设备的蓝牙功能
    public void onEnableBlueTooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 0);
        } else {
            Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_LONG).show();
        }
    }

    //使设备的蓝牙可被发现
    public void onDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(discoverableIntent);
    }

}
