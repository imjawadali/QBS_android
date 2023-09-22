package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shakerapplication.R;
import com.google.android.material.button.MaterialButton;

public class POSTAUDITSCREEN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postauditscreen);
        getSupportActionBar().hide();

    }

    public void skipbarcode(View view) {
        onBackPressed();
    }

    public void showpopup(View view) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(POSTAUDITSCREEN.this);
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

    public void gotoback(View view) {
        startActivity(new Intent(getApplicationContext(),FloorScanSerialnumber.class));
    }

    public void gotodashboard(View view) {
        startActivity(new Intent(getApplicationContext(),DashboardScreen.class));
        finishAffinity();
        finish();
    }
}