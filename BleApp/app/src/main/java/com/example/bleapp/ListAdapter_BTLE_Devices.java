package com.example.bleapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter_BTLE_Devices extends ArrayAdapter<BTLE_Device> {

    Activity activity;
    int layoutResourceID;
    ArrayList<BTLE_Device> devices;
    public ListAdapter_BTLE_Devices(@NonNull Context context, int resource, @NonNull ArrayList<BTLE_Device> objects) {
        super(context, resource, objects);

        this.activity = (Activity) context;
        this.layoutResourceID = resource;
        this.devices = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID, parent, false);
        }

        BTLE_Device device = devices.get(position);
        String name = device.getName();
        String address = device.getAddress();
        int rssi = device.getRSSI();

        TextView tv_name = (TextView)convertView.findViewById(R.id.tv_name);
        if(name != null && name.length() > 0)
        {
            tv_name.setText(device.getName());
        }
        else
        {
            tv_name.setText("No Name");
        }

        TextView tv_rssi = (TextView)convertView.findViewById(R.id.tv_rssi);
        tv_rssi.setText("RSSI:" + Integer.toString(rssi));

        TextView tv_address = (TextView)convertView.findViewById(R.id.tv_address);
        if(address != null && address.length() > 0) {
            tv_address.setText(address);
        }
        else
        {
            tv_address.setText("No Address");
        }
        return convertView;
    }
}
