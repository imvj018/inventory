package app.trial.warehouse.storagetype;

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

public class StMyAdapter extends RecyclerView.Adapter<StMyAdapter.ViewHolder> {

    private List<StListItem> stListItems;
    private Context context;

    public StMyAdapter(List<StListItem> stListItems, Context context) {
        this.stListItems = stListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public StMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_st_list_item,parent,false);
        return new StMyAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StMyAdapter.ViewHolder holder, int position) {
        final StListItem stlistItem = stListItems.get(position);


        holder.textViewHead.setText("Storage Type : " + stListItems.get(position).getStrtype());
        holder.textViewDesc1.setText("Location : " + stListItems.get(position).getLocation());
        holder.textViewDesc2.setText("Warehouse : " + stListItems.get(position).getWarehouse());

    }

    @Override
    public int getItemCount() {
        return stListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead,textViewDesc1,textViewDesc2;
        LinearLayout layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.linearlayout);
            textViewHead = itemView.findViewById(R.id.TextViewHead);
            textViewDesc1 = itemView.findViewById(R.id.TextViewDesc1);
            textViewDesc2 = itemView.findViewById(R.id.TextViewDesc2);
        }
    }
}
