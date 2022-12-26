package app.trial.warehouse.materialgroup;


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

public class MgTxAdapter extends RecyclerView.Adapter<MgTxAdapter.ViewHolder>{

    private List<MgItemList> mgItemLists;
    private Context context;

    public MgTxAdapter(List<MgItemList> mgItemLists, Context context) {
        this.mgItemLists = mgItemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MgTxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_mg_item_list,parent,false);
        return new MgTxAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MgTxAdapter.ViewHolder holder, int position) {
        final MgItemList mgItemList = mgItemLists.get(position);

        holder.textViewHead.setText("Material : " + mgItemLists.get(position).getMatcode()+" ("+ mgItemLists.get(position).getMatdesc()+")");
        holder.textViewDesc1.setText("UOM : " + mgItemLists.get(position).getBuom());

    }

    @Override
    public int getItemCount() {
        return mgItemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead,textViewDesc1;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.linearlayout1);
            textViewHead = itemView.findViewById(R.id.dltextview1);
            textViewDesc1 = itemView.findViewById(R.id.dltextview2);

        }
    }
}

