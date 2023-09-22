package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shakerapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class DashboardScreen extends AppCompatActivity {
    private CardView serialcheck, goodsreceipt, goodsissue, transferposting, flooraudit, loadinventory, good_issue_return;
    private ImageView logout;
    private  TextView logged_in_username , logged_in_plant, logged_in_stloc;
    private  SharedPreferences sharedPreferences;


    private  SpannableStringBuilder spannableStringBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);


    try {
        sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", MODE_PRIVATE);
        logged_in_username = findViewById(R.id.loggedin_username);
        logged_in_plant = findViewById(R.id.loggedin_plant);
        logged_in_stloc = findViewById(R.id.loggedin_stloc);



        //-----------------------SETTING UP USERNAME HERE-----------------------
        String username_text = "User: " +sharedPreferences.getString("username","");
        int splitIndex = username_text.indexOf(":") + 2;
        spannableStringBuilder = new SpannableStringBuilder(username_text);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, username_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logged_in_username.setText(spannableStringBuilder+" |");

        //-----------------------SETTING UP PLANT HERE-----------------------
        username_text = "Plant: " +sharedPreferences.getString("plant","");
        splitIndex = username_text.indexOf(":") + 2;
        spannableStringBuilder = new SpannableStringBuilder(username_text);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, username_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logged_in_plant.setText(spannableStringBuilder+" |");

        //-----------------------SETTING UP STORAGE LOCATION HERE-----------------------
        username_text = "STLoc: " +sharedPreferences.getString("storage_location","");
        splitIndex = username_text.indexOf(":") + 2;
        spannableStringBuilder = new SpannableStringBuilder(username_text);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, username_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logged_in_stloc.setText(spannableStringBuilder+" |");


        Objects.requireNonNull(getSupportActionBar()).hide();
        logout = findViewById(R.id.logout);
        initialization();
        onClickListener();
        logout.setOnClickListener(v -> {
            sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intent);
            finish();
        });
    }catch (Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    }

    private void onClickListener() {

    try{
        loadinventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Scan_Barcode_Screen.class));
            }
        });
        goodsissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowObdNumbers.class);
                intent.putExtra("user_doing_gi_type","LF");
                startActivity(intent);
            }
        });

        good_issue_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowObdNumbers.class);
                intent.putExtra("user_doing_gi_type","LR");
                startActivity(intent);
            }
        });



        serialcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SerialCheckScreen.class));
            }
        });
        goodsreceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectInboundDelivery.class));
            }
        });
        transferposting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Select_Type_Of_Transfer_Posting.class));
            }
        });
        flooraudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FloorAuditScreen.class));
            }
        });

    }catch (Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    }

    private void initialization() {
        serialcheck = findViewById(R.id.serialcheck);
        goodsreceipt = findViewById(R.id.goodsreceipt);
        goodsissue = findViewById(R.id.goodsissue);
        good_issue_return = findViewById(R.id.goodsissue_return);
        transferposting = findViewById(R.id.transferposting);
        loadinventory = findViewById(R.id.loadinventory);
        flooraudit = findViewById(R.id.flooraudit);
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
