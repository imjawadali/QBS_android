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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shakerapplication.Adapters.Select_Inbound_Delivery_Adapter;
import com.example.shakerapplication.ModelClasses.Goods_Receipt_Select_STO_ModelClass;
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

public class SelectInboundDelivery extends AppCompatActivity {
   ImageView skipinbound;
    EditText searchsto;
    ProgressDialog progressDialog;
    Select_Inbound_Delivery_Adapter datalist;
    private ArrayList<Goods_Receipt_Select_STO_ModelClass> filteredData = new ArrayList<>();
    private String plant;
    private String storageLoc;
    TextView stototalcount;
    ArrayList<Goods_Receipt_Select_STO_ModelClass> data = new ArrayList<>();
    private Routes routes;

    private String isUserComingFromTransferPosting="false";

   RecyclerView recycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_inbound_delivery);
     try {
         getSupportActionBar().hide();
         initialization();
         skipinbound.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });
         // context of your Android app
         // base URL of API
         // filename of the certificate in the assets folder
         // password for the certificate
         Retrofit retrofit = OkHttpUtils.createRetrofit(
                 this,  // context of your Android app
                 "https://bar.shaker.com.sa/",  // base URL of API
                 "bilal.pfx",  // filename of the certificate in the assets folder
                 "123123123"  // password for the certificate
         );
         routes = retrofit.create(Routes.class);
         SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
         plant = sharedPreferences.getString("plant", "Failed");
         storageLoc = sharedPreferences.getString("storage_location", "Failed");


         try {
             Intent intent = getIntent();
             System.out.println("What has been shared by transfer posting: " + intent.getStringExtra("move_stock_sloc_to_sloc"));
             if (intent.getStringExtra("move_stock_sloc_to_sloc").equals("false")) {
                 isUserComingFromTransferPosting = "true";
             } else {
                 isUserComingFromTransferPosting = "false";
             }
         } catch (Exception e) {
             System.out.println("No Intent Passed!");
         }

         retrieve_Data_From_GoodsIssue_STO(plant, storageLoc);

     }catch (Exception e){
         Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
         snackbar.show();
     }

    }

    private void initialization() {
      try {
          recycle = findViewById(R.id.recycle);
          skipinbound = findViewById(R.id.skipinbound);
          searchsto = findViewById(R.id.searchsto);
          searchsto.requestFocus();
          getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
          searchsto.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {
                  String searchText = s.toString().toLowerCase().trim();

                  if (searchText.isEmpty()) {
                      // if search field is empty, show full data
                      filteredData.clear();
                      filteredData.addAll(data);
                      datalist.notifyDataSetChanged();
                      stototalcount.setText("Total Count: " + String.valueOf(data.size()));
                  } else {
                      // filter data based on search text
                      filteredData.clear();
                      for (Goods_Receipt_Select_STO_ModelClass item : data) {
                          if (item.getEBELN().toLowerCase().contains(searchText)) {
                              filteredData.add(item);
                          }
                      }
                      datalist.notifyDataSetChanged();
                      stototalcount.setText("Total Count: " + String.valueOf(filteredData.size()));

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

    public void gotoscanproduct(View view) {
try {
    Intent i = new Intent(getApplicationContext(), ScanProductScreen.class);
    i.putExtra("stono", "");
    startActivity(i);
}catch (Exception e){
    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
    snackbar.show();
}
    }
    private void retrieve_Data_From_GoodsIssue_STO(String plant, String storageLoc){
        try {
            progressDialog = new ProgressDialog(SelectInboundDelivery.this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Data Fetching");
            progressDialog.setCancelable(false);
            progressDialog.show();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            recycle.setLayoutManager(linearLayoutManager);
            datalist = new Select_Inbound_Delivery_Adapter(data,filteredData,getApplicationContext(),isUserComingFromTransferPosting);
            recycle.setAdapter(datalist);
            String I_TPCHK="";
            if(isUserComingFromTransferPosting.equals("true")){
                I_TPCHK ="x";
            }
            else if(isUserComingFromTransferPosting.equals("false")){
                I_TPCHK ="";
            }
            Call<List<Goods_Receipt_Select_STO_ModelClass>> call = routes.getGoodsReceiptStoData(plant,storageLoc,I_TPCHK);

            call.enqueue(new Callback<List<Goods_Receipt_Select_STO_ModelClass>>() {
                @Override
                public void onResponse(Call<List<Goods_Receipt_Select_STO_ModelClass>> call, Response<List<Goods_Receipt_Select_STO_ModelClass>> response) {
                   if (response.isSuccessful()){
                       progressDialog.dismiss();
                       data.clear();
                       data.addAll(response.body());
                       datalist.notifyDataSetChanged();
                       filteredData.addAll(response.body());
                       stototalcount = findViewById(R.id.stototalcount);
                       stototalcount.setText("Total Count: "+String.valueOf(data.size()));
                   }
                }

                @Override
                public void onFailure(Call<List<Goods_Receipt_Select_STO_ModelClass>> call, Throwable t) {
                    Toast.makeText(SelectInboundDelivery.this, "An Error Occurred" +t, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    public void skipserialcheck(View view) {
      try {
          startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
          finish();
      }catch (Exception e){
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