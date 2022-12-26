package app.trial.warehouse.wmstock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.trial.warehouse.R;
import app.trial.warehouse.gr.dispgr2;


public class wmstockAdapter extends RecyclerView.Adapter<wmstockAdapter.wmstockViewHolder> {

    private Context mContext;
    private ArrayList<wmstockItem> mwmstockList;
    private OnItemClickListener mListener;

    public wmstockAdapter(ArrayList<wmstockItem> wmstockList) {

    }

    public wmstockAdapter(dispgr2 context, ArrayList<wmstockItem> mwmstockList) {
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public wmstockAdapter(Context context, ArrayList<wmstockItem> wmstockList) {
        mContext = context;
        mwmstockList = wmstockList;
    }

    @NonNull
    @Override
    public wmstockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.stock_item, parent, false);
        return new wmstockViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull wmstockViewHolder holder, int position) {
        wmstockItem currentItem = mwmstockList.get(position);


        String grncopy, material, description, quantity, uom, location, storage;
        grncopy = currentItem.getgrncopy();
        material = currentItem.getmaterial();
        description = currentItem.getmatdesc();
        quantity = currentItem.getquantity();
        uom = currentItem.getuom();
        location = currentItem.getlocation();
        storage = currentItem.getstoragetype();
        float checkqty = Float.parseFloat(quantity);
        if(checkqty > 0){
            holder.quantity.setTextColor(Color.parseColor("#007810"));
            holder.grnum.setTextColor(Color.parseColor("#007810"));
            checkqty = Math.abs(checkqty);
            quantity = String.valueOf(checkqty);
        }
        if(checkqty < 0){
            holder.quantity.setTextColor(Color.parseColor("#FF0000"));
            holder.grnum.setTextColor(Color.parseColor("#FF0000"));
            checkqty = Math.abs(checkqty);
            quantity = String.valueOf(checkqty);
        }

        holder.material.setText("Material : " + material + " (" + description + ")");
        holder.quantity.setText( quantity + " " + uom);
        holder.grnum.setText(grncopy);
        holder.location.setText("Location : " + location);
        holder.strtype.setText("Storage Type : " + storage);
    }

    @Override
    public int getItemCount() {
        return mwmstockList.size();
    }

    public class wmstockViewHolder extends RecyclerView.ViewHolder {

        public TextView material, quantity, grnum, location, strtype;


        public wmstockViewHolder(@NonNull View itemView) {
            super(itemView);


            material = itemView.findViewById(R.id.stockmatnum);
            quantity = itemView.findViewById(R.id.stockquantity);
            grnum = itemView.findViewById(R.id.stockgrncopy);
            location = itemView.findViewById(R.id.stocklocnumber);
            strtype = itemView.findViewById(R.id.stockstrtype);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
