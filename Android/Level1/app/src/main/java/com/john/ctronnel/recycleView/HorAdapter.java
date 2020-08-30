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

public class HorAdapter extends RecyclerView.Adapter<HorAdapter.LinearViewHoler> {

    private Context mContent;
    private LayoutInflater mInflater;
    private List<String> list;

    private OnItemClickListener mlistener;
    public HorAdapter(Context context, OnItemClickListener listener)
    {
        this.mContent = context;
        mInflater = LayoutInflater.from(context);
        this.mlistener = listener;
    }
    @NonNull
    @Override
    public HorAdapter.LinearViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LinearViewHoler holder = new LinearViewHoler(mInflater.inflate(R.layout.layout_hor_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHoler holder, final int position)
    {
        holder.textView.setText("Hello World");
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContent, "click.."+position, Toast.LENGTH_LONG).show();
                mlistener.onClick(position);
            }
        });
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

    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
