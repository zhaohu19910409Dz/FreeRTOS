package john.zhao.arunningman.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.util.List;

import john.zhao.arunningman.R;

public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.ViewHolder> {

    private List<Integer> list;

    public HomeBannerAdapter(List<Integer> list)
    {
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivPic.setImageResource(list.get(position % list.size()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RadiusImageView ivPic;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_ban_home);
        }
    }
}
