package com.john.ctronnel.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.john.ctronnel.R;

import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHoler> {

    private Context mContent;
    private LayoutInflater mInflater;
    private List<String> list;

    public LinearAdapter(Context context)
    {
        this.mContent = context;
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public LinearAdapter.LinearViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LinearViewHoler holder = new LinearViewHoler(mInflater.inflate(R.layout.layout_linear_recycle_view_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHoler holder, int position)
    {
        holder.textView.setText("Hello World");
    }


    @Override
    public int getItemCount()
    {
        return 30;
    }

    class LinearViewHoler extends  RecyclerView.ViewHolder{

        private TextView textView;
        public LinearViewHoler(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textMain);
        }
    }
}
