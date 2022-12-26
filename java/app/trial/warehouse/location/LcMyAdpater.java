package app.trial.warehouse.location;

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
import app.trial.warehouse.storagetype.StDispShow;

class LcMyAdapter extends RecyclerView.Adapter<LcMyAdapter.ViewHolder> {

    private List<LcListItem> lcListItems;
    private Context context;

    public LcMyAdapter(List<LcListItem> lcListItems, Context context) {
        this.lcListItems = lcListItems;
        this.context = context;
    }


    @NonNull
    @Override
    public LcMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_lc_list_item,parent,false);
        return new ViewHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LcMyAdapter.ViewHolder holder, int position) {
        final LcListItem lcListItem = lcListItems.get(position);


        holder.textViewHead.setText("Location : " + lcListItems.get(position).getLocation());
        holder.textViewDesc1.setText("Description : " + lcListItems.get(position).getLocdesc());
        holder.textViewDesc2.setText("Warehouse : " + lcListItems.get(position).getWhouse());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StDispShow.class);
                intent.putExtra("whouse",lcListItem.getWhouse());
                intent.putExtra("locnum",lcListItem.getLocation());
                intent.putExtra("locdesc",lcListItem.getLocdesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return lcListItems.size();
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

