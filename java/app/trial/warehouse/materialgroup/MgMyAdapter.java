package app.trial.warehouse.materialgroup;


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

import java.util.List;

import app.trial.warehouse.R;

public class MgMyAdapter extends RecyclerView.Adapter<MgMyAdapter.ViewHolder> {

    private List<MgListItem> mgListItems;
    private Context context;

    public MgMyAdapter(List<MgListItem> mgListItems, Context context) {
        this.mgListItems = mgListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MgMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_mg_list_item,parent,false);
        return new MgMyAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MgMyAdapter.ViewHolder holder, int position) {
        final MgListItem mglistItem = mgListItems.get(position);


        holder.textViewHead.setText("Material Group : " + mgListItems.get(position).getGrp());
        holder.textViewDesc1.setText("Description : " + mgListItems.get(position).getDesc());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MgDispText.class);
                intent.putExtra("grp",mglistItem.getGrp());
                intent.putExtra("description",mglistItem.getDesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mgListItems.size();
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
