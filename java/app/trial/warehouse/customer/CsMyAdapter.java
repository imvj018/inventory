package app.trial.warehouse.customer;

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

public class CsMyAdapter extends RecyclerView.Adapter<CsMyAdapter.ViewHolder> {

    private List<CsListItem> csListItems;
    private Context context;

    public CsMyAdapter(List<CsListItem> csListItems, Context context) {
        this.csListItems = csListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CsMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cs_list_item,parent,false);
        return new CsMyAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CsMyAdapter.ViewHolder holder, int position) {
        final CsListItem cslistItem = csListItems.get(position);


        holder.textViewHead.setText("Customer : " + csListItems.get(position).getCusgrp());
        holder.textViewDesc1.setText("Description : " + csListItems.get(position).getCusdesc());


    }



    @Override
    public int getItemCount() {
        return csListItems.size();
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

