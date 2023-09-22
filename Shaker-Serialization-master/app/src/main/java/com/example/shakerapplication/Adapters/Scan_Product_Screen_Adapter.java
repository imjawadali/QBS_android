package com.example.shakerapplication.Adapters;

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

import com.example.shakerapplication.ModelClasses.Goods_Receipt_Serial_Sto_Item_ModelClass;
import com.example.shakerapplication.ModelClasses.Transfer_GRN_Model_Class;
import com.example.shakerapplication.R;
import com.example.shakerapplication.Screens.ScanProductScreen;
import com.example.shakerapplication.Screens.ScanSerialNumberScreen;

import java.util.ArrayList;

public class Scan_Product_Screen_Adapter extends RecyclerView.Adapter<Scan_Product_Screen_Adapter.ViewHolder> {
    ArrayList<Goods_Receipt_Serial_Sto_Item_ModelClass> stolist;
    private ArrayList<Goods_Receipt_Serial_Sto_Item_ModelClass> filteredData = new ArrayList<>();
    Context context;
    String isUserComingFromTransferPosting;

    private Transfer_GRN_Model_Class[] transfer_grn_model_classes;
    public Scan_Product_Screen_Adapter(ArrayList<Goods_Receipt_Serial_Sto_Item_ModelClass> stolist, ArrayList<Goods_Receipt_Serial_Sto_Item_ModelClass> filteredData, Context context, String isUserComingFromTransferPosting, Transfer_GRN_Model_Class[] transfer_grn_model_classes) {
        this.stolist = stolist;
        this.filteredData = filteredData;
        this.context = context;
        this.isUserComingFromTransferPosting = isUserComingFromTransferPosting;
        this.transfer_grn_model_classes = transfer_grn_model_classes;
    }

    @NonNull
    @Override
    public Scan_Product_Screen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.scan_product_screen_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull Scan_Product_Screen_Adapter.ViewHolder holder, int position) {
        Goods_Receipt_Serial_Sto_Item_ModelClass item = filteredData.get(position);

        String stono = item.getEbeln();

//        String stono = stolist.get(position).getEbeln();
        String stoitem = stolist.get(position).getEbelp();
        String material = stolist.get(position).getMatnr();
        String materialdescription = stolist.get(position).getMaktx();
        String qty = stolist.get(position).getMenge();
        String aedat = stolist.get(position).getAedat();
        String bwart = stolist.get(position).getBwart();
        String ean11 = stolist.get(position).getEan11();
        String grund = stolist.get(position).getGrund();
        String lfimg = stolist.get(position).getLfimg();
        String lgort = stolist.get(position).getLgort();
        String meins = stolist.get(position).getMeins();
        String netpr = stolist.get(position).getNetpr();
        String netwr = stolist.get(position).getNetwr();
        String posnr = stolist.get(position).getPosnr();
        String reswk = stolist.get(position).getReswk();
        String serialno = stolist.get(position).getSerialno();
        String sernr = stolist.get(position).getSernr();
        String umlgo = stolist.get(position).getUmlgo();
        String umwrk = stolist.get(position).getUmwrk();
        String vbeln = stolist.get(position).getVbeln();
        String werks = stolist.get(position).getWerks();
        String gi_qty = stolist.get(position).getGi_qty();
        String sto_typ = stolist.get(position).getSto_typ();


        holder.stono.setText(stono);
        holder.stoitem.setText(stoitem);
        holder.material.setText(material);
        holder.materialdescription.setText(materialdescription);
        holder.qty.setText(qty);

        try {
            if (transfer_grn_model_classes.length != 0) {
                for(int f=0; f< transfer_grn_model_classes.length; f++){
                    if(transfer_grn_model_classes[f].getMATNR().equals(material)){
                       holder.imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

        }catch (Exception e){
            System.out.println("No Already Scanned Data Present!");
        }

        
        holder.scanproductcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ScanSerialNumberScreen.class);

              try {
                  if (transfer_grn_model_classes.length != 0) {
                      Bundle bundle = new Bundle();
                      bundle.putSerializable("transfer_grn_model_classes", transfer_grn_model_classes);
                      i.putExtras(bundle);
                  }

              }catch (Exception e){
                  System.out.println("No Already Scanned Data Present!");
              }

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stono",stono);
                i.putExtra("stoitem",stoitem);
                i.putExtra("material",material);
                i.putExtra("materialdescription",materialdescription);
                i.putExtra("qty",qty);
                i.putExtra("gi_qty", gi_qty);
                i.putExtra("aedat",aedat);
                i.putExtra("bwart",bwart);
                i.putExtra("ean11",ean11);
                i.putExtra("grund",grund);
                i.putExtra("lfimg",lfimg);
                i.putExtra("lgort",lgort);
                i.putExtra("meins",meins);
                i.putExtra("netpr",netpr);
                i.putExtra("netwr",netwr);
                i.putExtra("posnr",posnr);
                i.putExtra("reswk",reswk);
                i.putExtra("serialno",serialno);
                i.putExtra("sernr",sernr);
                i.putExtra("umlgo",umlgo);
                i.putExtra("umwrk",umwrk);
                i.putExtra("vbeln",vbeln);
                i.putExtra("werks",werks);
                i.putExtra("sto_typ",sto_typ);
                i.putExtra("isUserComingFromTransferPosting",isUserComingFromTransferPosting);
                context.startActivity(i);
            }
        });
    
    }



    @Override
    public int getItemCount() {
        return filteredData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stono,stoitem,material,materialdescription,qty;
        CardView scanproductcard;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stono = itemView.findViewById(R.id.stono);
            stoitem = itemView.findViewById(R.id.stoitem);
            material = itemView.findViewById(R.id.material);
            materialdescription = itemView.findViewById(R.id.materialdescription);
            qty = itemView.findViewById(R.id.qty);
            scanproductcard = itemView.findViewById(R.id.scanproductcard);
            imageView = itemView.findViewById(R.id.scannedGRNItemIndicator);
        }
    }
}
