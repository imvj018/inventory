package app.trial.warehouse.materialmaster;


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

public class MmMyAdapter extends RecyclerView.Adapter<MmMyAdapter.ViewHolder> {

    private List<MmListItem> mmListItems;
    private Context context;

    public MmMyAdapter(List<MmListItem> mmListItems, Context context) {
        this.mmListItems = mmListItems;
        this.context = context;
    }


    @NonNull
    @Override
    public MmMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_mm_list_item,parent,false);
        return new MmMyAdapter.ViewHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MmMyAdapter.ViewHolder holder, int position) {
        final MmListItem mmListItem = mmListItems.get(position);


        holder.textViewHead.setText("Material : " + mmListItems.get(position).getMatcode());
        holder.textViewDesc1.setText("Description : " + mmListItems.get(position).getMatdesc());
        holder.textViewDesc2.setText("Material Group : " + mmListItems.get(position).getMatgrp());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MmDispText.class);
                intent.putExtra("matcode",mmListItem.getMatcode());
                intent.putExtra("matdesc",mmListItem.getMatdesc());
                intent.putExtra("matgrp",mmListItem.getMatgrp());
                intent.putExtra("buom",mmListItem.getBuom());
                intent.putExtra("locnumber",mmListItem.getLocnum());
                intent.putExtra("ibroomtype",mmListItem.getIbtype());
                intent.putExtra("obroomtype",mmListItem.getObtype());
                intent.putExtra("wnumber",mmListItem.getWnumber());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mmListItems.size();
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

