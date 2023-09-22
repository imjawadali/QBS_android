package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.shakerapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class Select_Type_Of_Transfer_Posting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try{
        setContentView(R.layout.activity_select_type_of_transfer_posting);
        getSupportActionBar().hide();
       }
       catch (Exception e){
           Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
           snackbar.show();
       }
    }

  public void move_stock_sloc_to_sloc(View view){
//      try {
//          Intent intent = new Intent(this, ScanTransferScreen.class);
//          intent.putExtra("move_stock_sloc_to_sloc", "true");
//          startActivity(intent);
//          finish();
//      }catch (Exception e){
//          Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
//          snackbar.show();
//      }
          Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Option Disabled , please contact IT", Snackbar.LENGTH_LONG);
          snackbar.show();
    }

    public void move_stock_plant_to_plant(View view){
     try {
         Intent intent = new Intent(this, SelectInboundDelivery.class);
         intent.putExtra("move_stock_sloc_to_sloc", "false");
         startActivity(intent);
         finish();
     }catch (Exception e){
         Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
         snackbar.show();
     }
    }

    public void gotodashboard(View view) {
      try {
        startActivity(new Intent(getApplicationContext(),DashboardScreen.class));
          finishAffinity();
        finish();
      }
      catch (Exception e){
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