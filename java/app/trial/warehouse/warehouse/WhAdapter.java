package app.trial.warehouse.warehouse;
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
import app.trial.warehouse.location.LcDispShow;
import java.util.List;

public class WhAdapter extends RecyclerView.Adapter<WhAdapter.ViewHolder> {

    private List<WhListItem> whListItems;
    private Context context;

    public WhAdapter(List<WhListItem> whListItems, Context context) {
        this.whListItems = whListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_wh_list_item,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WhListItem whlistItem = whListItems.get(position);


        holder.textViewHead.setText("Warehouse : " + whListItems.get(position).getWnumber());
        holder.textViewDesc1.setText("Description : " + whListItems.get(position).getWdesc());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LcDispShow.class);
                intent.putExtra("wnumber",whlistItem.getWnumber());
                intent.putExtra("wdesc",whlistItem.getWdesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return whListItems.size();
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
