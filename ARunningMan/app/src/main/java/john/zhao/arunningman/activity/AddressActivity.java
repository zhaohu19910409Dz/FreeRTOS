package john.zhao.arunningman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import john.zhao.arunningman.BaseActivity;
import john.zhao.arunningman.R;
import john.zhao.arunningman.adapter.AddressRecAdapter;
import john.zhao.arunningman.room.Address;
import john.zhao.arunningman.room.AddressManager;

public class AddressActivity extends BaseActivity {

    private TextView tvAdd;
    private RecyclerView recyclerView;
    private AddressRecAdapter adapter;
    private AddressManager manager;
    private int TYPE = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        init();
        initView();
        initData();
        initEvent();
    }

    @Override
    public void init() {
        manager = new AddressManager(this);
    }

    @Override
    public void initView() {
        tvAdd = findViewById(R.id.tv_add_address);
        recyclerView =findViewById(R.id.rec_address);
    }

    @Override
    public void initData() {
        adapter = new AddressRecAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        manager.getAddressLive().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(List<Address> addresses) {
                adapter.setList(addresses);
            }
        });
    }

    @Override
    public void initEvent() {
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, SelectActivity.class);
                intent.putExtra("type", TYPE);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new AddressRecAdapter.onItemClickListener() {
            @Override
            public void onClick(Address address) {

            }
        });
    }
}
