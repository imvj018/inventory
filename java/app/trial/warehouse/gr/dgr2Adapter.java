package app.trial.warehouse.gr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.trial.warehouse.R;

public class dgr2Adapter extends RecyclerView.Adapter<dgr2Adapter.dgr2ViewHolder> {

    private Context mContext;
    private ArrayList<dgr2Item> mdgr2List;


    public dgr2Adapter(ArrayList<dgr2Item> dgr2List) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    public dgr2Adapter(Context context, ArrayList<dgr2Item> dgr2List) {
        mContext = context;
        mdgr2List = dgr2List;
    }

    @NonNull
    @Override
    public dgr2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dgr2item, parent, false);
        return new dgr2ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull dgr2ViewHolder holder, int position) {
        dgr2Item currentItem = mdgr2List.get(position);
        String material = currentItem.getmaterial();
        String matdesc = currentItem.getmatedesc();
        String quantity = currentItem.getquantity();
        String uom = currentItem.getuom();


        holder.item.setText("Material : " + material + " (" + matdesc + ")");
        holder.reqty.setText( quantity + " " + uom);

    }

    @Override
    public int getItemCount() {
        return mdgr2List.size();
    }

    public static class dgr2ViewHolder extends RecyclerView.ViewHolder {

        public TextView item, quantity;

        EditText avqty, reqty, location;

        public dgr2ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            quantity = itemView.findViewById(R.id.quantity);
            avqty = itemView.findViewById(R.id.avqty);
            reqty = itemView.findViewById(R.id.ipqty);
            location = itemView.findViewById(R.id.location);


        }
    }
}
