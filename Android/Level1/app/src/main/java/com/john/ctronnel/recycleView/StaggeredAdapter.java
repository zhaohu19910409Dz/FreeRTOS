package com.john.ctronnel.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.john.ctronnel.R;

import java.util.List;

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.LinearViewHoler> {

    private Context mContent;
    private LayoutInflater mInflater;
    private List<String> list;

    private OnItemClickListener mlistener;
    public StaggeredAdapter(Context context, OnItemClickListener listener)
    {
        this.mContent = context;
        mInflater = LayoutInflater.from(context);
        this.mlistener = listener;
    }
    @NonNull
    @Override
    public StaggeredAdapter.LinearViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LinearViewHoler holder = new LinearViewHoler(mInflater.inflate(R.layout.activity_staggered_recycler_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHoler holder, final int position)
    {
        if(position % 2 != 0) {
            holder.imgView.setImageResource(R.drawable.img2);
        }
        else {
            holder.imgView.setImageResource(R.drawable.img1);
        }
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        private ImageView imgView;
        public LinearViewHoler(@NonNull View itemView) {
            super(itemView);
            imgView = (ImageView)itemView.findViewById(R.id.iv);
        }
    }

    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
