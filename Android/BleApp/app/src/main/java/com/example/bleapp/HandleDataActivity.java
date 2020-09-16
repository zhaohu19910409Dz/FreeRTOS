package com.example.bleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HandleDataActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSend;
    private EditText editText;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1000)
            {
                Log.d("BLEAPP", "Send data");
                byte[] data = {11, 22, 33,44};
                //gattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                MyDeviceInstance.getInstance().getGattCharacteristic().setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                MyDeviceInstance.getInstance().getGattCharacteristic().setValue(data);
                MyDeviceInstance.getInstance().getGatt().writeCharacteristic(MyDeviceInstance.getInstance().getGattCharacteristic());
                handler.sendEmptyMessageDelayed(1000, 1000);
            }
            else if(msg.what == 1001)
            {
                BtnEnable(true);
            }
        }
    };

    private void BtnEnable(boolean enable)
    {
        btnSend.setEnabled(enable);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_data);

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        editText = findViewById(R.id.edit1);

        BluetoothDevice device = MyDeviceInstance.getInstance().getBluetoothDevice();
        MyGattCallBack myGattCallBack = new MyGattCallBack(handler);

        device.connectGatt(this, false, myGattCallBack);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnSend:
            {
                Log.d("BLEAPP", "Send data");
                byte[] data = {11, 22, 33,44};
                MyDeviceInstance.getInstance().getGattCharacteristic().setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                String info = editText.getText().toString();
                MyDeviceInstance.getInstance().getGattCharacteristic().setValue(info);
                MyDeviceInstance.getInstance().getGatt().writeCharacteristic(MyDeviceInstance.getInstance().getGattCharacteristic());
            }
            break;
        }
    }
}