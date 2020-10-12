package john.zhao.arunningman.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import john.zhao.arunningman.R;
import john.zhao.arunningman.room.Address;

public class AddressRecAdapter extends RecyclerView.Adapter<AddressRecAdapter.ViewHolder>
{

    private onItemClickListener listener;
    private List<Address> list;

    public  void setOnItemClickListener(AddressRecAdapter.onItemClickListener listener)
    {
        this.listener = listener;
    }

    public void setList(List<Address> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvName.setText(list.get(position).getName());
        holder.tvPhone.setText(list.get(position).getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvAddress;
        private TextView tvName;
        private TextView tvPhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAddress = itemView.findViewById(R.id.tv_address_address);
            tvName = itemView.findViewById(R.id.tv_name_address);
            tvPhone = itemView.findViewById(R.id.tv_phone_address);
        }
    }

    public interface onItemClickListener{
        public void onClick(Address address);
    }

}

