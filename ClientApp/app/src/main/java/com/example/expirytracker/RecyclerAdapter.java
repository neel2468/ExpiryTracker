package com.example.expirytracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE = 1;
    private final Context context;
    private final List<Object> listRecyclerItem;

    public RecyclerAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private  TextView productExpiryDate;
        private  TextView expiry_status;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productExpiryDate = (TextView) itemView.findViewById(R.id.proudctExpiryDate);
            expiry_status = (TextView) itemView.findViewById(R.id.expiryStatus);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case TYPE:
                default:
                    View layoutView = LayoutInflater.from(parent.getContext())
                                                    .inflate(R.layout.product_list_item,parent,false);

                    return new ItemViewHolder(layoutView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        int viewType = getItemViewType(i);
        switch (viewType) {
            case TYPE:
                default:
                    ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                    Products products = (Products) listRecyclerItem.get(i);
                    itemViewHolder.productName.setText(products.getName());
                    itemViewHolder.productExpiryDate.setText(products.getDate());
                    if(products.getDate().contains("2021")) {
                        itemViewHolder.expiry_status.setText("Not Expired");
                        itemViewHolder.expiry_status.setTextColor(Color.parseColor("#60AC5D"));
                    } else {
                        itemViewHolder.expiry_status.setText("Expired");
                        itemViewHolder.expiry_status.setTextColor(Color.parseColor("#EF5350"));
                    }

        }

    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
