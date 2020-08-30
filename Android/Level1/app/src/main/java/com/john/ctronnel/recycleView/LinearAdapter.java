package com.john.ctronnel.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.john.ctronnel.R;

import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContent;
    private LayoutInflater mInflater;
    private List<String> list;

    private OnItemClickListener mlistener;
    public LinearAdapter(Context context, OnItemClickListener listener)
    {
        this.mContent = context;
        mInflater = LayoutInflater.from(context);
        this.mlistener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == 0) {
            return new LinearViewHoler(mInflater.inflate(R.layout.layout_linear_recycle_view_item, parent, false));
        }
        else
        {
            return new LinearViewHoler2(mInflater.inflate(R.layout.layout_linear_item2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        if(getItemViewType(position) == 0)
        {
            ((LinearViewHoler)holder).textView.setText("Hello World");
            ((LinearViewHoler)holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContent, "click.."+position, Toast.LENGTH_LONG).show();
                    mlistener.onClick(position);
                }
            });
        }
        else
        {
            ((LinearViewHoler2)holder).textView.setText("Hello World");
            ((LinearViewHoler2)holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContent, "click.."+position, Toast.LENGTH_LONG).show();
                    mlistener.onClick(position);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position % 2 == 0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
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

    class LinearViewHoler2 extends  RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView img;
        public LinearViewHoler2(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textMain);
            img = (ImageView)itemView.findViewById(R.id.image);
        }
    }

    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
