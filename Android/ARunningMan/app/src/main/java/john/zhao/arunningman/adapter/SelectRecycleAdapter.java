package john.zhao.arunningman.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import john.zhao.arunningman.R;
import john.zhao.arunningman.model.SelectInfo;

public class SelectRecycleAdapter extends RecyclerView.Adapter<SelectRecycleAdapter.ViewHolder> {
    private List<SelectInfo> list;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<SelectInfo> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SelectRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_select, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectRecycleAdapter.ViewHolder holder, final int position) {
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvCity.setText(list.get(position).getCity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null)
                {
                    onItemClickListener.OnClick(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvAddress;
        private  TextView tvCity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tv_address_search_select);
            tvCity = itemView.findViewById(R.id.tv_city_search_select);
        }
    }

    public interface OnItemClickListener{
        void OnClick(SelectInfo selectInfo);
    }
}
