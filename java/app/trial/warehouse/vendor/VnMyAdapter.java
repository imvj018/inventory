package app.trial.warehouse.vendor;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.trial.warehouse.R;

public class VnMyAdapter extends RecyclerView.Adapter<VnMyAdapter.ViewHolder> {

    private List<VnListItem> vnListItems;
    private Context context;

    public VnMyAdapter(List<VnListItem> vnListItems, Context context) {
        this.vnListItems = vnListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public VnMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_vn_list_item,parent,false);
        return new VnMyAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VnMyAdapter.ViewHolder holder, int position) {
        final VnListItem vnlistItem = vnListItems.get(position);


        holder.textViewHead.setText("Vendor : " + vnListItems.get(position).getVcode());
        holder.textViewDesc1.setText("Description : " + vnListItems.get(position).getVdesc());


    }

    @Override
    public int getItemCount() {
        return vnListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead,textViewDesc1;
        LinearLayout layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.linearlayout);
            textViewHead = itemView.findViewById(R.id.TextViewHead);
            textViewDesc1 = itemView.findViewById(R.id.TextViewDesc1);
        }
    }
}