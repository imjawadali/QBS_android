package com.example.shakerapplication.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakerapplication.ModelClasses.Goods_Receipt_Select_STO_ModelClass;
import com.example.shakerapplication.ModelClasses.Load_Outbound_Delivery;
import com.example.shakerapplication.ModelClasses.Transfer_GI_Model_Class;
import com.example.shakerapplication.R;
import com.example.shakerapplication.Screens.GoodsIssueScreen;

import java.util.ArrayList;
import java.util.List;


public class Select_Outbound_Delivery_Adapter extends RecyclerView.Adapter<Select_Outbound_Delivery_Adapter.ViewHolder> {
    ArrayList<Load_Outbound_Delivery> loadoutbounddeliveries = new ArrayList<>();
    ArrayList<Load_Outbound_Delivery> filteredData = new ArrayList<>();
    Transfer_GI_Model_Class[] receivedArray;
    Context context;

    String obd_number, deliv_type;

    public Select_Outbound_Delivery_Adapter(ArrayList<Load_Outbound_Delivery> loadoutbounddeliveries, ArrayList<Load_Outbound_Delivery> filteredData, Context context, String obd_number,    Transfer_GI_Model_Class[] receivedArray, String deliv_type) {
        this.loadoutbounddeliveries = loadoutbounddeliveries;
        this.filteredData = filteredData;
        this.context = context;
        this.obd_number = obd_number;
        this.receivedArray = receivedArray;
        this.deliv_type = deliv_type;
    }

    @NonNull
    @Override
    public Select_Outbound_Delivery_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_outbound_delivery_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull Select_Outbound_Delivery_Adapter.ViewHolder holder, int position) {
        Load_Outbound_Delivery item = filteredData.get(holder.getAdapterPosition());
        String obdno = item.getVBELN();
        holder.obdno.setText(obdno);

        holder.itemno.setText(item.getPOSNR());
        holder.material.setText(item.getMATNR());
        holder.descr.setText(item.getMAKTX());
        holder.qty.setText(item.getLFIMG());

        try {
            if (receivedArray.length != 0) {
                for(int f=0; f< receivedArray.length; f++){
                    if(receivedArray[f].getMaterial().equals(item.getMATNR())){
                        holder.imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

        }catch (Exception e){
            System.out.println("No Already Scanned Data Present!");
        }


        holder.gotogoodsissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsIssueScreen.class);


                try {
                    if (receivedArray.length != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("transfer_gi_model_classes", receivedArray);
                        intent.putExtras(bundle);
                    }

                }catch (Exception e){
                    System.out.println("No Already Scanned Data Present!");
                }


                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("matnr", item.getMATNR());
                intent.putExtra("vbeln", item.getVBELN());
                intent.putExtra("posnr", item.getPOSNR());
                intent.putExtra("maktx", item.getMAKTX());
                intent.putExtra("lfimg", item.getLFIMG());
                intent.putExtra("ebeln", item.getEBELN());
                intent.putExtra("ebelp", item.getEBELP());
                intent.putExtra("werks", item.getWERKS());
                intent.putExtra("ean11", item.getEAN11());
                intent.putExtra("lgort", item.getLGORT());
                intent.putExtra("menge", item.getMENGE());
                intent.putExtra("meins", item.getMEINS());
                intent.putExtra("netpr", item.getNETPR());
                intent.putExtra("netwr", item.getNETWR());
                intent.putExtra("aedat", item.getAEDAT());
                intent.putExtra("reswk", item.getRESWK());
                intent.putExtra("serialno", item.getSERIALNO());
                intent.putExtra("bwart", item.getBWART());
                intent.putExtra("grund", item.getGRUND());
                intent.putExtra("umwrk", item.getUMWRK());
                intent.putExtra("umlgo", item.getUMLGO());
                intent.putExtra("sernr", item.getSERNR());
                intent.putExtra("obd_number_selected", obd_number);
                intent.putExtra("serial_chk",item.getSERIAL_CHK());
                intent.putExtra("delivery_type", deliv_type);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView obdno, itemno, material, descr, qty;
        CardView gotogoodsissue;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            obdno = itemView.findViewById(R.id.obdno);
            itemno = itemView.findViewById(R.id.itemno);
            material = itemView.findViewById(R.id.material);
            descr = itemView.findViewById(R.id.descr);
            qty = itemView.findViewById(R.id.qty);
            gotogoodsissue = itemView.findViewById(R.id.gotogoodsissue);
            imageView = itemView.findViewById(R.id.scannedGRNItems);
        }
    }
}
