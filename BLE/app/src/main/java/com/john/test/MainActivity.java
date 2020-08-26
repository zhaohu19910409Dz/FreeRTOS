package com.john.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

//https://blog.csdn.net/kelvin_hwk/article/details/99819788

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;

    // 使用一个类成员保存BluetoothGattServer引用
    private BluetoothGattServer bluetoothGattServer;

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

        String strUID = "0000fddf-0000-1000-8000-00805f9b34fb";
        UUID YOUR_SERVICE_UUID = UUID.fromString(strUID);
        //新建一个GATT服务
        BluetoothGattService service = new BluetoothGattService(YOUR_SERVICE_UUID, BluetoothGattService.SERVICE_TYPE_PRIMARY);

        //新建一个GATT特征值
        UUID YOUR_CHARACTERISTIC_UUID = UUID.fromString(strUID);
        // 注意，如果要使特征值可写，PROPERTY需设置PROPERTY_WRITE（服务端需要返回响应）或PROPERTY_WRITE_NO_RESPONSE（服务端无需返回响应），
        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(YOUR_CHARACTERISTIC_UUID,
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY | BluetoothGattCharacteristic.PROPERTY_WRITE,
                BluetoothGattCharacteristic.PERMISSION_READ | BluetoothGattCharacteristic.PERMISSION_WRITE);


        //新建一个特征值描述（可选）
        UUID YOUR_DESCRIPTER_UUID = UUID.fromString(strUID);
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(YOUR_DESCRIPTER_UUID, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);

        //服务加入特征值
        service.addCharacteristic(characteristic);

        // onCreate方法中或某个按钮触发的回调函数中
        bluetoothGattServer = bluetoothManager.openGattServer(this, new BluetoothGattServerCallback() {
            ByteArrayOutputStream bleDataByteArray;
            BluetoothGattDescriptor writingObj;
            @Override
            public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
                // 连接状态改变
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d("kaikai", "BLE服务端:BLE Connected!");
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d("kaikai", "BLE服务端:BLE Disconnected!");
                }
            }

            @Override
            public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {

                Log.d("kaikai", "BLE服务端:接收到特征值读请求!" + " requestId=" + requestId + " offset=" + offset);

                BluetoothGattCharacteristic gattCharacteristic = bluetoothGattServer.getService(TIME_SERVICE_UUID).getCharacteristic(TIME_CHARACTERISTIC_UUID);
                if (characteristic == gattCharacteristic) {
                    // 证明characteristic与通过gattServer得到的是同一个对象
                    Log.d("kaikai", "BLE服务端:same characteristic!");
                }

                byte[] value = characteristic.getValue();
                if (value == null) {
                    value = "init responseData".getBytes();
                    characteristic.setValue(value);
                }

                // offset != 0，如果读的偏移不为0，证明是分段读，返回特征值的对应便宜的截断数据就行（每次返回的长度都至末尾）
                if (offset != 0) {
                    int newLen = value.length - offset;
                    byte[] retVal = new byte[value.length - offset];
                    System.arraycopy(value, offset, retVal, 0, newLen);
                    value = retVal;
                }

                // 请求读特征
                if (TIME_CHARACTERISTIC_UUID.equals(characteristic.getUuid())) {
                    bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
                }
            }

            @Override
            public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
                // super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);

                Log.d("kaikai", "BLE Server端 onCharacteristicWriteRequest"
                        + " requestId:" + requestId + " offset:" + offset + " prepareWrite:" + preparedWrite
                        + " responseNeeded:" + responseNeeded + " value:" + new String(value));

                // 不是分段写
                if (!preparedWrite) {
                    characteristic.setValue(value);
                }
                // 分段写
                else {
                    if (offset == 0) {
                        bleDataByteArray = new ByteArrayOutputStream();
                    }
                    try {
                        bleDataByteArray.write(value);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    writingObj = characteristic;
                }

                // 尝试用更改后的数据作为响应，发现客户端的回调的状态值为133，不为0
                byte[] valueWrong = new byte[value.length];
                System.arraycopy(value, 0, valueWrong, 0, value.length);
                valueWrong[0]++;

                if (responseNeeded) {
                    // 注意，如果写特征值需要响应（特征值的属性是PROPERTY_WRITE不是PROPERTY_WRITE_NO_RESPONSE）,必需发送value作为响应数据
                    bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
                    // 此处为使用错误的数据做响应
//                      bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, valueWrong);
                }
            }

            @Override
            public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
                super.onExecuteWrite(device, requestId, execute);

                // 当分段写时，才会回调此方法

                Log.d("kaikai", "onExecuteWrite called! requestId=" + requestId + " execute=" + execute);

                if (execute) {
                    byte[] data = bleDataByteArray.toByteArray();
                    String dataStr = new String(data);
                    Log.d("kaikai", "onExecuteWrite 拼接数据:" + dataStr);

//                        BluetoothGattCharacteristic characteristic1 = bluetoothGattServer.getService(TIME_SERVICE_UUID).getCharacteristic(TIME_CHARACTERISTIC_UUID);
//                        characteristic1.setValue(data);
                    if (writingObj != null) {
                        if (writingObj instanceof BluetoothGattCharacteristic) {
                            ((BluetoothGattCharacteristic) writingObj).setValue(data);
                        } else if (writingObj instanceof BluetoothGattDescriptor) {
                            ((BluetoothGattDescriptor) writingObj).setValue(data);
                        } else {
                            throw new RuntimeException("writingObj类型不明");
                        }
                    } else {
                        throw new RuntimeException("writingObj为空");
                    }

                    // 注意，当写数据过长时，会自动分片，多次调用完onCharacteristicWriteRequest后，便会调用此方法，要在此方法中发送响应，execute参数指示是否执行成功，可按照此参数发送响应的状态
                    bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, null);
                } else {
                    Log.d("kaikai", "BLE SERVER: onExecuteWrite发送失败响应");
                    // 发送一个失败响应
                    bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, 0, null);
                }
            }

            @Override
            public void onServiceAdded(int status, BluetoothGattService service) {
                super.onServiceAdded(status, service);

                Log.d("kaikai", "onServiceAdded");
            }

            @Override
            public void onMtuChanged(BluetoothDevice device, int mtu) {
                super.onMtuChanged(device, mtu);
                Log.d("kaikai", "BLE SERVER:onMTUChanged! mtu=" + mtu);


//                    bluetoothGattServer.sendResponse(device,0,0,0,null);
            }

            @Override
            public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
                super.onDescriptorReadRequest(device, requestId, offset, descriptor);

                Log.d("kaikai", "BLE服务端:onDescriptorReadRequest  requestId=" + requestId + "  offset=" + offset);

                byte[] value = descriptor.getValue();
                if (value == null) {
                    value = "descriptor1 value".getBytes();
                }

                // 与characteristic的处理一样
                if (offset > 0) {
                    int newLen = value.length - offset;
                    byte[] newValue = new byte[newLen];
                    System.arraycopy(value, offset, newValue, 0, newLen);
                    value = newValue;
                }

                bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
            }

            @Override
            public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
                super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);

                Log.d("kaikai", "BLE服务端:onDescriptorWriteRequest  requestId=" + requestId + "  preparedWrite=" + preparedWrite + "  responseNeeded=" + responseNeeded
                        + "  value=" + new String(value));

                if (!preparedWrite) {
                    descriptor.setValue(value);
                } else {
                    if (offset == 0) {
                        bleDataByteArray = new ByteArrayOutputStream();
                    }
                    try {
                        bleDataByteArray.write(value);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    writingObj = descriptor;
                }

                if (responseNeeded) {
                    bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
                }

            }
        });

        //GATT服务端加入刚才创建的GATT Service
        bluetoothGattServer.addService(service);

        //开始发送BLE广播
        /**
         * 通知服务开启（发广告）
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void startAdvertising() {
            BluetoothLeAdvertiser advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
            if (advertiser == null) {
                Log.d("kaikai", "advertiser为空");
                return;
            }

            AdvertiseSettings settings = new AdvertiseSettings.Builder().setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                    .setConnectable(true)
                    .setTimeout(0)
                    .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                    .build();

            AdvertiseData data = new AdvertiseData.Builder().setIncludeDeviceName(true)
                    .setIncludeTxPowerLevel(false)
                    .addServiceUuid(new ParcelUuid(TIME_SERVICE_UUID))
                    .build();

            advertiser.startAdvertising(settings, data, new AdvertiseCallback() {
                @Override
                public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                    Log.d("kaikai", "BLE Advertise started!");
                }

                @Override
                public void onStartFailure(int errorCode) {

                    Log.d("kaikai", "BLE Advertise fail!errorCode=" + errorCode);
                }
            });
        }

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
