package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shakerapplication.Adapters.Select_Outbound_Delivery_Adapter;
import com.example.shakerapplication.ModelClasses.Goods_Issue_Serial_number_Data_Api;
import com.example.shakerapplication.ModelClasses.Goods_Receipt_Select_STO_ModelClass;
import com.example.shakerapplication.ModelClasses.Load_Outbound_Delivery;
import com.example.shakerapplication.ModelClasses.Load_Storage_Location;
import com.example.shakerapplication.ModelClasses.Transfer_GI_Model_Class;
import com.example.shakerapplication.ModelClasses.Transfer_GRN_Model_Class;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SelectOutboundDeliveryScreen extends AppCompatActivity {
RecyclerView recycle;
EditText searchobd;
TextView count, show_deliv_type;
String obdno;
private String selected_obd_number, serial_chk, deliv_type;
ProgressDialog progressDialog;
Select_Outbound_Delivery_Adapter select_outbound_delivery_adapter;
ArrayList<Load_Outbound_Delivery> loadoutbounddeliveries = new ArrayList<>();

ArrayList<Load_Outbound_Delivery> loadoutbounddeliveries_backup = new ArrayList<>();
ArrayList<Load_Outbound_Delivery> filteredData = new ArrayList<>();
    private  Transfer_GI_Model_Class[] receivedArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_outbound_delivery_screen);
       try {

           getSupportActionBar().hide();
           initialization();

           SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
           String plant = sharedPreferences.getString("plant", "");
           String storageloc = sharedPreferences.getString("storage_location", "");

           System.out.println("Shared Preference Value: " + plant);
           System.out.println("Shared Preference Value: " + storageloc);
           Intent intent = getIntent();
           selected_obd_number = intent.getStringExtra("obd_number_selected");
           serial_chk = intent.getStringExtra("serial_chk");
           deliv_type = intent.getStringExtra("delivery_type");


           if(deliv_type.equalsIgnoreCase("LF")){
                show_deliv_type.setText("NORMAL");
           }
           else if(deliv_type.equalsIgnoreCase("LR")){
                show_deliv_type.setText("RETURN");
           }


           try {
               receivedArray = (Transfer_GI_Model_Class[]) getIntent().getSerializableExtra("transfer_gi_model_classes");
               System.out.println("The size of the received array is: " + receivedArray.length);
           } catch (Exception e) {
               System.out.println("No Bundle Passed!");
           }

           Load_Outbound_Delivery(plant, storageloc);
       }catch (Exception e){
           Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
           snackbar.show();
       }

    }



    private void initialization() {

try {
    recycle = findViewById(R.id.recycle);
    searchobd = findViewById(R.id.searchobd);
    show_deliv_type = findViewById(R.id.deliv_type_disp);
    searchobd.requestFocus();
    searchobd.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchText = s.toString().toLowerCase().trim();

            if (searchText.isEmpty()) {
                System.out.println("Now Moving to filter by obd");
                filterRecyclerViewByOBD(selected_obd_number);
            } else {
                filterRecyclerViewByModelNumber(searchText);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    });
}catch (Exception e){
    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
    snackbar.show();
}

    }


    private void filterRecyclerViewByOBD(String searchText){

    try {
        if (searchText.isEmpty()) {
            // if search field is empty, show full data
            filteredData.clear();
            filteredData.addAll(loadoutbounddeliveries);
            select_outbound_delivery_adapter.notifyDataSetChanged();
        } else {
            // filter data based on search text

            if (loadoutbounddeliveries_backup.size() != 0) {
                loadoutbounddeliveries.clear();
                loadoutbounddeliveries.addAll(loadoutbounddeliveries_backup);
                System.out.println("The size of backup is: " + loadoutbounddeliveries_backup.size());
            }

            filteredData.clear();

            for (Load_Outbound_Delivery item : loadoutbounddeliveries) {
                if (item.getVBELN().toLowerCase().contains(searchText)) {
                    filteredData.add(item);
                }
            }

            if (loadoutbounddeliveries_backup.size() == 0) {
                loadoutbounddeliveries.clear();
                loadoutbounddeliveries.addAll(filteredData);
                loadoutbounddeliveries_backup.addAll(filteredData);
            }


            select_outbound_delivery_adapter.notifyDataSetChanged();
            count.setText(String.valueOf("Total Count: " + filteredData.size()));
        }

    }catch (Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    }



    private void filterRecyclerViewByModelNumber(String searchText){
   try {
       if (searchText.isEmpty()) {
       } else {
           // filter data based on search text
           filteredData.clear();
           loadoutbounddeliveries.clear();
           loadoutbounddeliveries.addAll(loadoutbounddeliveries_backup);
           for (Load_Outbound_Delivery item : loadoutbounddeliveries) {
               if (item.getEAN11().toLowerCase().contains(searchText)) {
//                    System.out.println("EAN is: "+item.getEAN11());
                   System.out.println("MAKTX is: " + item.getMAKTX());
//                    System.out.println("VBELN is: "+item.getVBELN());
                   filteredData.add(item);
               }
           }
           select_outbound_delivery_adapter.notifyDataSetChanged();
           count.setText(String.valueOf("Total Count: " + filteredData.size()));
       }
   }catch (Exception e){
       Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
       snackbar.show();
   }
    }




    public void skipserialcheck(View view) {
        onBackPressed();
    }

    private void Load_Outbound_Delivery(String plant,String storageloc){
        try {
            progressDialog = new ProgressDialog(SelectOutboundDeliveryScreen.this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Data Fetching");
            progressDialog.setCancelable(false);
            progressDialog.show();
                //Configuring SSL and Making Retrofit Instance
                Retrofit retrofit = OkHttpUtils.createRetrofit(
                        this,  // context of your Android app
                        "https://bar.shaker.com.sa/",  // base URL of API
                        "bilal.pfx",  // filename of the certificate in the assets folder
                        "123123123"  // password for the certificate
                );

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectOutboundDeliveryScreen.this,LinearLayoutManager.VERTICAL,false);
            recycle.setLayoutManager(linearLayoutManager);
            select_outbound_delivery_adapter = new Select_Outbound_Delivery_Adapter(loadoutbounddeliveries,filteredData,getApplicationContext(), selected_obd_number, receivedArray,deliv_type);
            recycle.setAdapter(select_outbound_delivery_adapter);


            Routes routes = retrofit.create(Routes.class);
            Call<List<Load_Outbound_Delivery>> call = routes.getOutboundDeliveries(plant,storageloc, deliv_type);
            call.enqueue(new Callback<List<Load_Outbound_Delivery>>() {
                @Override
                public void onResponse(Call<List<Load_Outbound_Delivery>> call, Response<List<Load_Outbound_Delivery>> response) {
                    if (response.isSuccessful() && response!=null){
                        progressDialog.dismiss();
                        loadoutbounddeliveries.clear();
                         loadoutbounddeliveries.addAll(response.body());
                        filteredData.addAll(response.body());
                          select_outbound_delivery_adapter.notifyDataSetChanged();


                          for (int i=0;i<loadoutbounddeliveries.size();i++){
//                              System.out.println("This is obdno"+loadoutbounddeliveries.get(i).getVBELN());
                              obdno = loadoutbounddeliveries.get(i).getVBELN();
                          }

                        count = findViewById(R.id.count);

                        filterRecyclerViewByOBD(selected_obd_number.toLowerCase().trim());
                    }
                }

                @Override
                public void onFailure(Call<List<Load_Outbound_Delivery>> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! Please Try Again Later", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            });
        }
        catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }



    public void gotodashboard(View view) {
        try {
            startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
            finishAffinity();
            finish();
        }catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//        finishAffinity();
//    }




}