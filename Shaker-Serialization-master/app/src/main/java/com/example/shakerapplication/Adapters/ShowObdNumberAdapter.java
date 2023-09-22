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

import com.example.shakerapplication.ModelClasses.Load_Outbound_Delivery;
import com.example.shakerapplication.R;
import com.example.shakerapplication.Screens.GoodsIssueScreen;
import com.example.shakerapplication.Screens.SelectOutboundDeliveryScreen;

import java.util.ArrayList;
import java.util.List;

public class ShowObdNumberAdapter extends RecyclerView.Adapter<ShowObdNumberAdapter.ViewHolder> {
    Context context;
    ArrayList<Load_Outbound_Delivery> load_outbound_deliveries = new ArrayList<>();

    public ShowObdNumberAdapter(Context context, ArrayList<Load_Outbound_Delivery> load_outbound_deliveries) {
        this.context = context;
        this.load_outbound_deliveries = load_outbound_deliveries;
    }

    @NonNull
    @Override
    public ShowObdNumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.showobdnumbercard,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowObdNumberAdapter.ViewHolder holder, int position) {
       Load_Outbound_Delivery data = load_outbound_deliveries.get(position);

        holder.obdno.setText(data.getVBELN());

        holder.name.setText(data.getNAME1());
        holder.date.setText(data.getWADAT());

        holder.line_items_qty.setText(data.getTOT_ITEMS());

        holder.total_qty.setText(data.getTOT_QTY());


        if(data.getDELIV_TYPE().equals("LF")) {
            holder.delivery_type.setText("Normal");
        }

        else{
            holder.delivery_type.setText("Return");
        }


             holder.obdcard.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(context,SelectOutboundDeliveryScreen.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     intent.putExtra("obd_number_selected", holder.obdno.getText().toString());
                     intent.putExtra("serial_chk", data.getSERIAL_CHK());
                     intent.putExtra("delivery_type", data.getDELIV_TYPE());

                     context.startActivity(intent);
                 }
             });
    }

    @Override
    public int getItemCount() {
        return load_outbound_deliveries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView obdcard;
        TextView obdno, name, date, delivery_type, line_items_qty, total_qty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            obdcard = itemView.findViewById(R.id.obdcard);
            obdno = itemView.findViewById(R.id.obdno);
            name = itemView.findViewById(R.id.obd_name);
            date = itemView.findViewById(R.id.obd_date);
            delivery_type = itemView.findViewById(R.id.obd_delivery_type);
            line_items_qty = itemView.findViewById(R.id.obd_line_items_qty);
            total_qty = itemView.findViewById(R.id.obd_total_qty);
        }
    }
}
