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

import com.example.shakerapplication.Adapters.Scan_Product_Screen_Adapter;
import com.example.shakerapplication.ModelClasses.Goods_Receipt_Select_STO_ModelClass;
import com.example.shakerapplication.ModelClasses.Goods_Receipt_Serial_Sto_Item_ModelClass;
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

public class ScanProductScreen extends AppCompatActivity {
    RecyclerView recycle;
    Scan_Product_Screen_Adapter scan_product_screen_adapter;
    private Retrofit retrofit;
    private Routes routes;
    private String plant;
    private String storageLoc;
    private String stono;
    private String isUserComingFromTransferPosting="false";
    EditText selectsto;
    ProgressDialog progressDialog;
    ArrayList<Goods_Receipt_Serial_Sto_Item_ModelClass> stolist = new ArrayList<>();
    private ArrayList<Goods_Receipt_Serial_Sto_Item_ModelClass> filteredData = new ArrayList<>();
    private  Transfer_GRN_Model_Class[] receivedArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_product_screen);
        try{

        getSupportActionBar().hide();
        retrofit = OkHttpUtils.createRetrofit(
                this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );
        routes = retrofit.create(Routes.class);
        initialization();
        SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
        plant = sharedPreferences.getString("plant", "Failed");
        storageLoc = sharedPreferences.getString("storage_location", "Failed");
        Intent i = getIntent();
        stono = i.getStringExtra("stono");
        SharedPreferences sharedPreferences1 = getSharedPreferences("stonumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("stono", stono);
        editor.apply();
        System.out.println("The STO is: => " + stono);
        System.out.println(plant);
        System.out.println(storageLoc);
        isUserComingFromTransferPosting = i.getStringExtra("isUserComingFromTransferPosting");

        try {
            receivedArray = (Transfer_GRN_Model_Class[]) getIntent().getSerializableExtra("transfer_grn_model_classes");
            System.out.println("The size of the received array is: " + receivedArray.length);
//            i.getStringExtra()
        } catch (Exception e) {
            System.out.println("No Bundle Passed!");
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(linearLayoutManager);
        scan_product_screen_adapter = new Scan_Product_Screen_Adapter(stolist, filteredData, getApplicationContext(), isUserComingFromTransferPosting, receivedArray);
        recycle.setAdapter(scan_product_screen_adapter);

        if(isUserComingFromTransferPosting.equalsIgnoreCase("true")) {
            getGoodsReceiptSerialStoItemData(stono, plant, "TRF");
        }
        else if(isUserComingFromTransferPosting.equalsIgnoreCase("false")) {
            getGoodsReceiptSerialStoItemData(stono, plant, "GRN");
        }


    } catch(Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }


    private void initialization() {

        try {

            recycle = findViewById(R.id.recycle);
            selectsto = findViewById(R.id.selectsto);
            selectsto.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            selectsto.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String searchText = s.toString().toLowerCase().trim();

                    if (searchText.isEmpty()) {
                        // if search field is empty, show full data
                        filteredData.clear();
                        filteredData.addAll(stolist);
                        scan_product_screen_adapter.notifyDataSetChanged();
                    } else {
                        // filter data based on search text
                        filteredData.clear();
                        for (Goods_Receipt_Serial_Sto_Item_ModelClass item : stolist) {
//                        System.out.println("The Model Number is :=> "+item.getEan11());
                            if (item.getEan11().toLowerCase().contains(searchText)) {
                                filteredData.add(item);
//                            System.out.println("I am here now");
                            }
                        }
                        scan_product_screen_adapter.notifyDataSetChanged();
//                    stototalcount.setText("Total Count: " + String.valueOf(filteredData.size()));
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


    private void getGoodsReceiptSerialStoItemData(String stono,String plant,String storageLoc) {

        try{
            progressDialog = new ProgressDialog(ScanProductScreen.this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Data Fetching");
            progressDialog.setCancelable(false);
            progressDialog.show();
            try {
                Call<List<Goods_Receipt_Serial_Sto_Item_ModelClass>> call = routes.getGoodsReceiptSerialStoData(stono, plant, storageLoc);
                call.enqueue(new Callback<List<Goods_Receipt_Serial_Sto_Item_ModelClass>>() {
                    @Override
                    public void onResponse(Call<List<Goods_Receipt_Serial_Sto_Item_ModelClass>> call, Response<List<Goods_Receipt_Serial_Sto_Item_ModelClass>> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response != null) {
                            stolist.clear();
                            stolist.addAll(response.body());
                            filteredData.addAll(response.body());
                            scan_product_screen_adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Goods_Receipt_Serial_Sto_Item_ModelClass>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

    } catch(Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    public void scanproduct(View view) {
        onBackPressed();
    }

    public void gotodashboard(View view) {
        startActivity(new Intent(getApplicationContext(),DashboardScreen.class));
        finishAffinity();
        finish();
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