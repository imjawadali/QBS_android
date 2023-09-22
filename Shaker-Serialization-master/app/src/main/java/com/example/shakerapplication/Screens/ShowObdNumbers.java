package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shakerapplication.Adapters.ShowObdNumberAdapter;
import com.example.shakerapplication.ModelClasses.Load_Outbound_Delivery;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowObdNumbers extends AppCompatActivity {
RecyclerView obdrecycle;
    ProgressDialog progressDialog;
    private Retrofit retrofit;
    ArrayList<Load_Outbound_Delivery> dataList = new ArrayList<>();
    private Routes routes;
    private String user_doing_gi_type;

ShowObdNumberAdapter showObdNumberAdapter;
LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_obd_numbers);

try{
        getSupportActionBar().hide();
        initialization();

        progressDialog = new ProgressDialog(ShowObdNumbers.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Fetching OBD#.....");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //Configuring SSL and Making Retrofit Instance
        retrofit = OkHttpUtils.createRetrofit(
                this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );

        routes = retrofit.create(Routes.class);
        SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
        String plant = sharedPreferences.getString("plant", "failed");
        String storageLoc = sharedPreferences.getString("storage_location", "failed");
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        obdrecycle.setLayoutManager(linearLayoutManager);
        getObdNumberData(plant, storageLoc);


    }catch(Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    }

    private void initialization() {
        obdrecycle = findViewById(R.id.obdrecycle);
        Intent intent = getIntent();
        user_doing_gi_type =  intent.getStringExtra("user_doing_gi_type");
    }

    public void skipserialcheck(View view) {
        onBackPressed();
    }

    public void getObdNumberData(String plant,String storageLoc){

        try {

            Call<List<Load_Outbound_Delivery>> call = routes.getOutboundDeliveries(plant, storageLoc, user_doing_gi_type);
            call.enqueue(new Callback<List<Load_Outbound_Delivery>>() {
                @Override
                public void onResponse(Call<List<Load_Outbound_Delivery>> call, Response<List<Load_Outbound_Delivery>> response) {

                    showObdNumberAdapter = new ShowObdNumberAdapter(getApplicationContext(), dataList);

                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        obdrecycle.setAdapter(showObdNumberAdapter);

                        List<Load_Outbound_Delivery> dataListduplicate = response.body();
                        HashSet<String> vbelnSet = new HashSet<>();
                        List<Load_Outbound_Delivery> dataListWithoutDuplicates = new ArrayList<>();

                        if (dataListduplicate.size() != 0) {

                            for (Load_Outbound_Delivery item : dataListduplicate) {
                                if (vbelnSet.add(item.getVBELN())) {
                                    dataListWithoutDuplicates.add(item);
                                }
                            }

                            TextView textView = findViewById(R.id.countobd);
                            textView.setText("Total Count: " + dataListWithoutDuplicates.size());
                            dataList.addAll(dataListWithoutDuplicates);
                            showObdNumberAdapter.notifyDataSetChanged();
                        } else {
                            Snackbar sb = Snackbar.make(findViewById(android.R.id.content).getRootView(), "No OBD Numbers Found!", Snackbar.LENGTH_LONG);
                            sb.show();
                            progressDialog.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Load_Outbound_Delivery>> call, Throwable t) {
                    Snackbar sb = Snackbar.make(findViewById(android.R.id.content).getRootView(), "An Error Occured! CAUSED BY: " + t.getMessage(), Snackbar.LENGTH_LONG);
                    sb.show();
                    progressDialog.dismiss();
                }
            });
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