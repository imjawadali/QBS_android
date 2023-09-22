package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.shakerapplication.R;
import com.google.android.material.button.MaterialButton;

public class SelectDestinationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destination_screen);
        getSupportActionBar().hide();

    }

    public void skipdestination(View view) {
        onBackPressed();
    }

    public void gotopopup(View view) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(SelectDestinationScreen.this);
        View view1 =getLayoutInflater().inflate(R.layout.popupmessage, null);
        alertDialog.setView(view1);
        alertDialog.setCancelable(true);
        AlertDialog dialog= alertDialog.create();
        dialog.show();
        MaterialButton btn = view1.findViewById(R.id.gotodashboard);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
            }
        });
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