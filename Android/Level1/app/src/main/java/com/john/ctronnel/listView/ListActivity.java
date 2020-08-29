package com.john.ctronnel.listView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.john.ctronnel.R;

public class ListActivity extends Activity {

    private ListView list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        list = (ListView)findViewById(R.id.listview1);
        list.setAdapter(new MyListAdapter(ListActivity.this));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, "click pos:"+position, Toast.LENGTH_LONG).show();
                //jump to other activity
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, "long press pos:"+position, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}
