package app.trial.warehouse.del;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.trial.warehouse.R;

import java.util.List;

public class DLMyAdapter extends RecyclerView.Adapter<DLMyAdapter.ViewHolder> {

    private final List<app.trial.warehouse.del.DLItemList> dlitemLists;
    private Context context;

    public DLMyAdapter(List<app.trial.warehouse.del.DLItemList> dlitemLists, Context context) {
        this.dlitemLists = dlitemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dlitem_list,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final app.trial.warehouse.del.DLItemList dlitemList = dlitemLists.get(position);

        holder.textViewHead1.setText("Material Number : "+ dlitemLists.get(position).getMatnum());
        holder.textViewHead2.setText("Quantity : "+ dlitemLists.get(position).getQuantity() + " " +  dlitemLists.get(position).getUom());

    }

    @Override
    public int getItemCount() {
        return dlitemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead1,textViewHead2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHead1 = itemView.findViewById(R.id.TextViewHead1);
            textViewHead2 = itemView.findViewById(R.id.TextViewHead2);
        }
    }
}
