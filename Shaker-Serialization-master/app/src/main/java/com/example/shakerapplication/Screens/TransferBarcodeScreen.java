package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.shakerapplication.Adapters.Scan_Serial_Number_Adapter;
import com.example.shakerapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class TransferBarcodeScreen extends AppCompatActivity {
RecyclerView recycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_barcode_screen);
     try {
         getSupportActionBar().hide();
         initialization();
     }catch (Exception e){
         Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
         snackbar.show();
     }
    }



    private void initialization() {

    try {
        recycle = findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(linearLayoutManager);
        Scan_Serial_Number_Adapter scan_serial_number_adapter = new Scan_Serial_Number_Adapter(getApplicationContext());
        recycle.setAdapter(scan_serial_number_adapter);
    }
    catch (Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    }

    public void cancel(View view) {
        onBackPressed();
    }

    public void gotobarcodescreen(View view) {
        startActivity(new Intent(getApplicationContext(),DeliveryScreen.class));
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