package app.trial.warehouse.unitofmeasure;

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

public class UmMyAdapter extends RecyclerView.Adapter<UmMyAdapter.ViewHolder>{

    private List<UmListItem> umListItems;
    private Context context;

    public UmMyAdapter(List<UmListItem> umListItems, Context context) {
        this.umListItems = umListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public UmMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_um_list_item,parent,false);
        return new UmMyAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UmMyAdapter.ViewHolder holder, int position) {
        final UmListItem umlistItem = umListItems.get(position);


        holder.textViewHead.setText("Unit : " + umListItems.get(position).getUmid()+"("+ umListItems.get(position).getUmdesc()+")");

    }



    @Override
    public int getItemCount() {
        return umListItems.size();
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

