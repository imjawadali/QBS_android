package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shakerapplication.ModelClasses.Goods_Receipt_Select_STO_ModelClass;
import com.example.shakerapplication.R;
import com.google.android.material.button.MaterialButton;

public class BarcodeScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_screen);
        getSupportActionBar().hide();
    }

    public void skipbarcode(View view) {
        onBackPressed();
    }

    public void submitbarcode(View view) {

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(BarcodeScreen.this);
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
        finish();
    }
}