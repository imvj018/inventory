package app.trial.warehouse.del;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.trial.warehouse.R;
import app.trial.warehouse.warehouse.WhListItem;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<DLListItem> dllistItems;
    private final Context context;

    public MyAdapter(List<DLListItem> dllistItems, Context context) {
        this.dllistItems = dllistItems;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dllist_item,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DLListItem dllistItem = dllistItems.get(position);


        holder.textViewHead.setText("Delivery Number : " + dllistItems.get(position).getDelnum());
        holder.textViewDesc1.setText("Ship To Party : " + dllistItems.get(position).getShiparty());
        holder.textViewDesc2.setText("Created Time : " + dllistItems.get(position).getCtime());

        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DLDisplayShow.class);
            intent.putExtra("id",dllistItem.getId());
            intent.putExtra("ctime",dllistItem.getCtime());
            intent.putExtra("dtime",dllistItem.getDtime());
            intent.putExtra("shiparty",dllistItem.getShiparty());
            intent.putExtra("delnum",dllistItem.getDelnum());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return dllistItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

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
