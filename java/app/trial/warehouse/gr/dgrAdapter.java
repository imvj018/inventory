package app.trial.warehouse.gr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.trial.warehouse.R;

public class dgrAdapter extends RecyclerView.Adapter<dgrAdapter.dgrViewHolder> {

    private Context mContext;
    private ArrayList<dgrItem> mdgrList;
    private OnItemClickListener mListener;

    public dgrAdapter(ArrayList<dgrItem> dgrList) {

    }

    public dgrAdapter(dispgr2 context, ArrayList<dgr2Item> mdgr2List) {
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public dgrAdapter(Context context, ArrayList<dgrItem> dgrList) {
        mContext = context;
        mdgrList = dgrList;
    }

    @NonNull
    @Override
    public dgrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dgr_item, parent, false);
        return new dgrViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull dgrViewHolder holder, int position) {
        dgrItem currentItem = mdgrList.get(position);
        String grn = currentItem.getGrn();
        String vendor = currentItem.getVendor();
        String warehouse = currentItem.getWarehouse();
        holder.grn.setText("Goods Receipt No : " + grn);
        holder.vendor.setText("Vendor : " + vendor);
        holder.warehouse.setText("Warehouse : " + warehouse);
    }

    @Override
    public int getItemCount() {
        return mdgrList.size();
    }

    public class dgrViewHolder extends RecyclerView.ViewHolder {

        public TextView grn, vendor, warehouse;


        public dgrViewHolder(@NonNull View itemView) {
            super(itemView);

            grn = itemView.findViewById(R.id.grnum);
            vendor = itemView.findViewById(R.id.vendor);
            warehouse = itemView.findViewById(R.id.warehouse);


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
