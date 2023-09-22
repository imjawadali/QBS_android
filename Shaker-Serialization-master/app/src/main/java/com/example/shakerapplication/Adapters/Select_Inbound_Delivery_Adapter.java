package com.example.shakerapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakerapplication.ModelClasses.Goods_Receipt_Select_STO_ModelClass;
import com.example.shakerapplication.R;
import com.example.shakerapplication.Screens.ScanProductScreen;

import java.util.ArrayList;

public class Select_Inbound_Delivery_Adapter extends RecyclerView.Adapter<Select_Inbound_Delivery_Adapter.ViewHolder> {
    ArrayList<Goods_Receipt_Select_STO_ModelClass> data;
    private final ArrayList<Goods_Receipt_Select_STO_ModelClass> dataFiltered;
    Context context;
String isUserComingFromTransferPosting;
    public Select_Inbound_Delivery_Adapter(ArrayList<Goods_Receipt_Select_STO_ModelClass> data, ArrayList<Goods_Receipt_Select_STO_ModelClass> dataFiltered, Context context, String isUserComingFromTransferPosting) {
        this.data = data;
        this.dataFiltered = dataFiltered;
        this.context = context;
        this.isUserComingFromTransferPosting = isUserComingFromTransferPosting;
    }

    @NonNull
    @Override
    public Select_Inbound_Delivery_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_inbound_delivery_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Select_Inbound_Delivery_Adapter.ViewHolder holder, int position) {
        Goods_Receipt_Select_STO_ModelClass item = dataFiltered.get(position);
        String stono = item.getEBELN();
        String date = item.getAEDAT();
        String supply_plant = item.getRESWK();
        String line_items_quantity = item.getTOT_ITEMS();
        String total_quantity = item.getTOT_QTY();

        holder.stono.setText(stono);
        holder.date.setText("DATE: "+date);
        holder.supply_plant.setText("SUPPLY PLANT: "+supply_plant);
        holder.line_items.setText("Line Items QTY: "+line_items_quantity);
        holder.total_qty.setText("Total QTY: "+total_quantity);


        holder.stocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ScanProductScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stono",stono);
                i.putExtra("isUserComingFromTransferPosting", isUserComingFromTransferPosting);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataFiltered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stono, date, supply_plant, line_items, total_qty;
        CardView stocard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stono = itemView.findViewById(R.id.stono);
            stocard = itemView.findViewById(R.id.stocard);
            date = itemView.findViewById(R.id.sto_date);
            supply_plant = itemView.findViewById(R.id.sto_supply_plant);
            line_items = itemView.findViewById(R.id.sto_total_line_items);
            total_qty = itemView.findViewById(R.id.sto_total_qty);
        }
    }
}
